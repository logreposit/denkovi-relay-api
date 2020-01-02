package com.logreposit.denkovi.denkovirelayapi.communication.serial;

import com.fazecast.jSerialComm.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DenkoviSerialClientImpl implements DenkoviSerialClient
{
    private static final Logger logger = LoggerFactory.getLogger(DenkoviSerialClientImpl.class);
    private static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("00");

    private final String communicationPort;
    private final long readTimeout;

    public DenkoviSerialClientImpl(String communicationPort, long readTimeout)
    {
        this.communicationPort = communicationPort;
        this.readTimeout = readTimeout;
    }

    @Override
    public Denkovi16ChannelRelayState get()
    {
        byte[] command = new byte[] {'a', 's', 'k', '/', '/'};
        byte[] result = this.executeCommand(command, 2);

        Denkovi16ChannelRelayState state = Denkovi16ChannelRelayState.fromByteArray(result);

        logger.info("Retrieved relay states: {}", state.getAll());

        return state;
    }

    @Override
    public void set(int number, boolean state)
    {
        if (number < 0 || number > 15)
        {
            logger.error("relay number must be 0-15. input was {}", number);

            throw new IllegalArgumentException("relay number must be 0-15");
        }

        char[] numberChars = NUMBER_FORMAT.format(number + 1).toCharArray();
        byte operator = getRelayOperator(state);
        byte[] command = new byte[] { (byte) numberChars[0], (byte) numberChars[1], operator, '/', '/' };

        this.executeCommandAndExpectSameAnswer(command);
    }

    @Override
    public void set(Denkovi16ChannelRelayState relayState)
    {
        byte[] states = relayState.toByteArray();
        byte[] command = new byte[] {'x', states[0], states[1], '/', '/'};

        this.executeCommandAndExpectSameAnswer(command);
    }

    private static byte getRelayOperator(boolean state)
    {
        if (state)
        {
            return '+';
        }

        return '-';
    }

    private void executeCommandAndExpectSameAnswer(byte[] command)
    {
        byte[] result = this.executeCommand(command, command.length);

        if (!Arrays.equals(command, result))
        {
            logger.error(
                    "Unable to execute command: Expected answer to be the same as the command sent. Expected: {}, Actual: {}",
                    command,
                    result
            );

            throw new RuntimeException("Unable to execute command: Expected answer to be the same as the command sent");
        }
    }

    private synchronized byte[] executeCommand(byte[] request, int numberOfBytesToRead)
    {
        Date started = new Date();

        SerialPort serialPort = SerialPort.getCommPort(this.communicationPort);

        configureSerialPortForDenkoviSerialRelay(serialPort);

        try
        {
            serialPort.openPort();
            serialPort.writeBytes(request, request.length);

            byte[] bytesRead = this.readFromSerialPort(serialPort, numberOfBytesToRead);
            long difference = new Date().getTime() - started.getTime();

            logger.info("Command {} took {} ms", request, difference);

            return bytesRead;
        }
        finally
        {
            serialPort.closePort();
        }
    }

    private byte[] readFromSerialPort(SerialPort serialPort, int numberOfBytes)
    {
        List<Byte> bytesRead = new ArrayList<>();

        Date started = new Date();

        while (true)
        {
            Date now = new Date();

            long differenceInMillis = now.getTime() - started.getTime();

            if (differenceInMillis > this.readTimeout)
            {
                logger.error(
                        "Attempt to read {} bytes from serial port timed out after {} ms",
                        numberOfBytes,
                        differenceInMillis
                );

                throw new RuntimeException("Timeout while reading data from serial port");
            }

            int bytesAvailable = serialPort.bytesAvailable();

            if (bytesAvailable < 1)
            {
                continue;
            }

            byte[] readBuffer = new byte[bytesAvailable];
            int numRead = serialPort.readBytes(readBuffer, readBuffer.length);

            logger.info("Read {} bytes of {} available", numRead, bytesAvailable);

            for (byte b : readBuffer)
            {
                bytesRead.add(b);
            }

            if (bytesRead.size() >= numberOfBytes)
            {
                return byteListToArray(bytesRead);
            }
        }
    }

    private static void configureSerialPortForDenkoviSerialRelay(SerialPort serialPort)
    {
        serialPort.setBaudRate(9600);
        serialPort.setNumDataBits(8);
        serialPort.setNumStopBits(1);
        serialPort.setParity(SerialPort.NO_PARITY);
        serialPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
    }

    private static byte[] byteListToArray(List<Byte> bytes)
    {
        byte[] array = new byte[bytes.size()];

        for (int i = 0; i < array.length; i++)
        {
            array[i] = bytes.get(i);
        }

        return array;
    }
}
