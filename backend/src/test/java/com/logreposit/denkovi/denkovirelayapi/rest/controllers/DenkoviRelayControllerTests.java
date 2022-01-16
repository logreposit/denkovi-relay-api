package com.logreposit.denkovi.denkovirelayapi.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.Relay;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.RelayState;
import com.logreposit.denkovi.denkovirelayapi.rest.mapper.ProcedureMapper;
import com.logreposit.denkovi.denkovirelayapi.services.DenkoviRelayService;
import com.logreposit.denkovi.denkovirelayapi.services.procedure.ProcedureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {DenkoviRelayController.class})
public class DenkoviRelayControllerTests
{
    @Autowired
    private MockMvc controller;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DenkoviRelayService denkoviRelayService;

    @Test
    public void testGetAll() throws Exception
    {
        List<Relay> relays = getSampleRelayList();

        when(this.denkoviRelayService.get()).thenReturn(relays);

        MockHttpServletRequestBuilder request = get("/v1/api/relays");

        this.controller.perform(request)
                       .andDo(print())
                       .andExpect(status().isOk())
                       .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                       .andExpect(jsonPath("$").isArray())
                       .andExpect(jsonPath("$[0].number").value(1))
                       .andExpect(jsonPath("$[0].state").value(RelayState.OFF.toString()))
                       .andExpect(jsonPath("$[1].number").value(2))
                       .andExpect(jsonPath("$[1].state").value(RelayState.ON.toString()))
                       .andExpect(jsonPath("$[2].number").value(3))
                       .andExpect(jsonPath("$[2].state").value(RelayState.OFF.toString()))
                       .andExpect(jsonPath("$[3].number").value(4))
                       .andExpect(jsonPath("$[3].state").value(RelayState.ON.toString()))
                       .andExpect(jsonPath("$[4].number").value(5))
                       .andExpect(jsonPath("$[4].state").value(RelayState.OFF.toString()))
                       .andExpect(jsonPath("$[5].number").value(6))
                       .andExpect(jsonPath("$[5].state").value(RelayState.ON.toString()))
                       .andExpect(jsonPath("$[6].number").value(7))
                       .andExpect(jsonPath("$[6].state").value(RelayState.OFF.toString()))
                       .andExpect(jsonPath("$[7].number").value(8))
                       .andExpect(jsonPath("$[7].state").value(RelayState.ON.toString()))
                       .andExpect(jsonPath("$[8].number").value(9))
                       .andExpect(jsonPath("$[8].state").value(RelayState.OFF.toString()))
                       .andExpect(jsonPath("$[9].number").value(10))
                       .andExpect(jsonPath("$[9].state").value(RelayState.ON.toString()))
                       .andExpect(jsonPath("$[10].number").value(11))
                       .andExpect(jsonPath("$[10].state").value(RelayState.OFF.toString()))
                       .andExpect(jsonPath("$[11].number").value(12))
                       .andExpect(jsonPath("$[11].state").value(RelayState.ON.toString()))
                       .andExpect(jsonPath("$[12].number").value(13))
                       .andExpect(jsonPath("$[12].state").value(RelayState.OFF.toString()))
                       .andExpect(jsonPath("$[13].number").value(14))
                       .andExpect(jsonPath("$[13].state").value(RelayState.ON.toString()))
                       .andExpect(jsonPath("$[14].number").value(15))
                       .andExpect(jsonPath("$[14].state").value(RelayState.OFF.toString()))
                       .andExpect(jsonPath("$[15].number").value(16))
                       .andExpect(jsonPath("$[15].state").value(RelayState.ON.toString()));

        verify(this.denkoviRelayService, times(1)).get();
    }

    @Test
    public void testSetAll() throws Exception
    {
        List<Relay> relays = getSampleRelayList();
        String jsonContent = this.objectMapper.writeValueAsString(relays);

        MockHttpServletRequestBuilder request =
                post("/v1/api/relays")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON_UTF8);

        when(this.denkoviRelayService.get()).thenReturn(relays);

        this.controller.perform(request)
                       .andDo(MockMvcResultHandlers.print())
                       .andExpect(status().isOk())
                       .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                       .andExpect(jsonPath("$").isArray())
                       .andExpect(jsonPath("$[0].number").value(1))
                       .andExpect(jsonPath("$[0].state").value(RelayState.OFF.toString()))
                       .andExpect(jsonPath("$[1].number").value(2))
                       .andExpect(jsonPath("$[1].state").value(RelayState.ON.toString()))
                       .andExpect(jsonPath("$[2].number").value(3))
                       .andExpect(jsonPath("$[2].state").value(RelayState.OFF.toString()))
                       .andExpect(jsonPath("$[3].number").value(4))
                       .andExpect(jsonPath("$[3].state").value(RelayState.ON.toString()))
                       .andExpect(jsonPath("$[4].number").value(5))
                       .andExpect(jsonPath("$[4].state").value(RelayState.OFF.toString()))
                       .andExpect(jsonPath("$[5].number").value(6))
                       .andExpect(jsonPath("$[5].state").value(RelayState.ON.toString()))
                       .andExpect(jsonPath("$[6].number").value(7))
                       .andExpect(jsonPath("$[6].state").value(RelayState.OFF.toString()))
                       .andExpect(jsonPath("$[7].number").value(8))
                       .andExpect(jsonPath("$[7].state").value(RelayState.ON.toString()))
                       .andExpect(jsonPath("$[8].number").value(9))
                       .andExpect(jsonPath("$[8].state").value(RelayState.OFF.toString()))
                       .andExpect(jsonPath("$[9].number").value(10))
                       .andExpect(jsonPath("$[9].state").value(RelayState.ON.toString()))
                       .andExpect(jsonPath("$[10].number").value(11))
                       .andExpect(jsonPath("$[10].state").value(RelayState.OFF.toString()))
                       .andExpect(jsonPath("$[11].number").value(12))
                       .andExpect(jsonPath("$[11].state").value(RelayState.ON.toString()))
                       .andExpect(jsonPath("$[12].number").value(13))
                       .andExpect(jsonPath("$[12].state").value(RelayState.OFF.toString()))
                       .andExpect(jsonPath("$[13].number").value(14))
                       .andExpect(jsonPath("$[13].state").value(RelayState.ON.toString()))
                       .andExpect(jsonPath("$[14].number").value(15))
                       .andExpect(jsonPath("$[14].state").value(RelayState.OFF.toString()))
                       .andExpect(jsonPath("$[15].number").value(16))
                       .andExpect(jsonPath("$[15].state").value(RelayState.ON.toString()));

        verify(this.denkoviRelayService, times(1)).set(eq(relays));
        verify(this.denkoviRelayService, times(1)).get();
    }

    @Test
    public void testGet() throws Exception
    {
        when(this.denkoviRelayService.get(eq(4))).thenReturn(new Relay(4, RelayState.OFF));

        MockHttpServletRequestBuilder request = get("/v1/api/relays/4");

        this.controller.perform(request)
                       .andDo(print())
                       .andExpect(status().isOk())
                       .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                       .andExpect(jsonPath("$.number").value(4))
                       .andExpect(jsonPath("$.state").value(RelayState.OFF.toString()));

        verify(this.denkoviRelayService, times(1)).get(eq(4));
    }

    @Test
    public void testOn() throws Exception
    {
        MockHttpServletRequestBuilder request = post("/v1/api/relays/5/on");

        when(this.denkoviRelayService.get(eq(5))).thenReturn(new Relay(5, RelayState.ON));

        this.controller.perform(request)
                       .andDo(MockMvcResultHandlers.print())
                       .andExpect(status().isOk())
                       .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                       .andExpect(jsonPath("$.number").value(5))
                       .andExpect(jsonPath("$.state").value(RelayState.ON.toString()));

        verify(this.denkoviRelayService, times(1)).set(eq(5), eq(RelayState.ON));
        verify(this.denkoviRelayService, times(1)).get(eq(5));
    }

    @Test
    public void testOff() throws Exception
    {
        MockHttpServletRequestBuilder request = post("/v1/api/relays/6/off");

        when(this.denkoviRelayService.get(eq(6))).thenReturn(new Relay(6, RelayState.OFF));

        this.controller.perform(request)
                       .andDo(MockMvcResultHandlers.print())
                       .andExpect(status().isOk())
                       .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                       .andExpect(jsonPath("$.number").value(6))
                       .andExpect(jsonPath("$.state").value(RelayState.OFF.toString()));

        verify(this.denkoviRelayService, times(1)).set(eq(6), eq(RelayState.OFF));
        verify(this.denkoviRelayService, times(1)).get(eq(6));
    }

    @Test
    public void testPulseSignalOnOff() throws Exception
    {
        MockHttpServletRequestBuilder request = post("/v1/api/relays/7/pulse-signal/on-off");

        when(this.denkoviRelayService.get(eq(7))).thenReturn(new Relay(7, RelayState.OFF));

        this.controller.perform(request)
                       .andDo(MockMvcResultHandlers.print())
                       .andExpect(status().isOk())
                       .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                       .andExpect(jsonPath("$.number").value(7))
                       .andExpect(jsonPath("$.state").value(RelayState.OFF.toString()));

        verify(this.denkoviRelayService, times(1)).set(eq(7), eq(RelayState.ON));
        verify(this.denkoviRelayService, times(1)).set(eq(7), eq(RelayState.OFF));
        verify(this.denkoviRelayService, times(1)).get(eq(7));
    }

    @Test
    public void testPulseSignalOffOn() throws Exception
    {
        MockHttpServletRequestBuilder request = post("/v1/api/relays/7/pulse-signal/off-on");

        when(this.denkoviRelayService.get(eq(7))).thenReturn(new Relay(7, RelayState.ON));

        this.controller.perform(request)
                       .andDo(MockMvcResultHandlers.print())
                       .andExpect(status().isOk())
                       .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                       .andExpect(jsonPath("$.number").value(7))
                       .andExpect(jsonPath("$.state").value(RelayState.ON.toString()));

        verify(this.denkoviRelayService, times(1)).set(eq(7), eq(RelayState.OFF));
        verify(this.denkoviRelayService, times(1)).set(eq(7), eq(RelayState.ON));
        verify(this.denkoviRelayService, times(1)).get(eq(7));
    }

    private static List<Relay> getSampleRelayList()
    {
        List<Relay> relays = new ArrayList<>();

        relays.add(new Relay(1, RelayState.OFF));
        relays.add(new Relay(2, RelayState.ON));
        relays.add(new Relay(3, RelayState.OFF));
        relays.add(new Relay(4, RelayState.ON));
        relays.add(new Relay(5, RelayState.OFF));
        relays.add(new Relay(6, RelayState.ON));
        relays.add(new Relay(7, RelayState.OFF));
        relays.add(new Relay(8, RelayState.ON));
        relays.add(new Relay(9, RelayState.OFF));
        relays.add(new Relay(10, RelayState.ON));
        relays.add(new Relay(11, RelayState.OFF));
        relays.add(new Relay(12, RelayState.ON));
        relays.add(new Relay(13, RelayState.OFF));
        relays.add(new Relay(14, RelayState.ON));
        relays.add(new Relay(15, RelayState.OFF));
        relays.add(new Relay(16, RelayState.ON));

        return relays;
    }
}
