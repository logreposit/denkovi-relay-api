package com.logreposit.denkovi.denkovirelayapi.persistence.repositories;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Procedure;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.RelayOffTask;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.RelayOnTask;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.SleepTask;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Step;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.dizitart.no2.Nitrite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProcedureRepositoryImplTests
{
    private ProcedureRepositoryImpl procedureRepository;

    @BeforeEach
    public void setUp()
    {
        String databaseFilePath = System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID().toString() + ".db";

        Nitrite nitrite = Nitrite.builder()
                                 .filePath(databaseFilePath)
                                 .openOrCreate();

        this.procedureRepository = new ProcedureRepositoryImpl(nitrite);
    }

    @Test
    public void testInsert()
    {
        Procedure procedure = getSampleProcedure();

        Procedure inserted = this.procedureRepository.save(procedure);

        assertThat(inserted).isNotNull();
        assertThat(inserted).isEqualTo(procedure);
    }

    @Test
    public void testInsertAndRetrieve()
    {
        Procedure procedure = getSampleProcedure();

        Procedure inserted = this.procedureRepository.save(procedure);

        assertThat(inserted).isNotNull();
        assertThat(inserted).isEqualTo(procedure);

        Optional<Procedure> retrievedOptional = this.procedureRepository.get(procedure.getId());

        assertThat(retrievedOptional).isPresent();
        assertThat(retrievedOptional.get()).isEqualTo(procedure);
    }

    @Test
    public void testInsertAndUpdateAndRetrieve()
    {
        Procedure procedure = getSampleProcedure();

        Procedure inserted = this.procedureRepository.save(procedure);

        assertThat(inserted).isNotNull();
        assertThat(inserted).isEqualTo(procedure);

        Procedure newProcedure = getSampleProcedure();

        newProcedure.setId(procedure.getId());
        newProcedure.setName("another name");
        newProcedure.setDescription("another description");
        newProcedure.setSteps(Arrays.asList(procedure.getSteps().get(3), procedure.getSteps().get(4)));

        Procedure updated = this.procedureRepository.save(newProcedure);

        assertThat(updated).isNotNull();
        assertThat(updated).isEqualTo(newProcedure);

        Optional<Procedure> retrievedOptional = this.procedureRepository.get(procedure.getId());

        assertThat(retrievedOptional).isPresent();
        assertThat(retrievedOptional.get()).isEqualTo(newProcedure);
    }

    private static Procedure getSampleProcedure()
    {
        RelayOnTask garageDoorMainPowerRelayOnTask = new RelayOnTask();

        garageDoorMainPowerRelayOnTask.setRelayNumber(1);

        Step step1 = new Step();

        step1.setName("Garage Door Main Power On");
        step1.setDescription("Switch on relay for garage main power connection");
        step1.setOrder(100);
        step1.setTask(garageDoorMainPowerRelayOnTask);

        SleepTask sleepTask1 = new SleepTask();

        sleepTask1.setMilliseconds(1000);

        Step step2 = new Step();

        step2.setName("Sleep after garage main power relay on");
        step2.setDescription("Sleep 1 second after switching on relay for garage main power connection");
        step2.setOrder(200);
        step2.setTask(sleepTask1);

        RelayOnTask pushButtonOnTask = new RelayOnTask();

        pushButtonOnTask.setRelayNumber(2);

        Step step3 = new Step();

        step3.setName("Garage Door Push Button On");
        step3.setDescription("Garage Door Push Button - On");
        step3.setOrder(300);
        step3.setTask(pushButtonOnTask);

        RelayOffTask pushButtonOffTask = new RelayOffTask();

        pushButtonOffTask.setRelayNumber(2);

        Step step4 = new Step();

        step4.setName("Garage Door Push Button Off");
        step4.setDescription("Garage Door Push Button - Off");
        step4.setOrder(400);
        step4.setTask(pushButtonOffTask);

        SleepTask sleepTask2 = new SleepTask();

        sleepTask2.setMilliseconds(30000);

        Step step5 = new Step();

        step5.setName("Sleep after garage pushbutton");
        step5.setDescription("Sleep 30 seconds after garage pushbutton action");
        step5.setOrder(500);
        step5.setTask(sleepTask2);

        RelayOffTask garageDoorMainPowerRelayOffTask = new RelayOffTask();

        garageDoorMainPowerRelayOffTask.setRelayNumber(1);

        Step step6 = new Step();

        step1.setName("Garage Door Main Power Off");
        step1.setDescription("Switch off relay for garage main power connection");
        step6.setOrder(600);
        step6.setTask(garageDoorMainPowerRelayOffTask);

        List<Step> steps = new ArrayList<>();

        steps.add(step1);
        steps.add(step2);
        steps.add(step3);
        steps.add(step4);
        steps.add(step5);
        steps.add(step6);

        Procedure procedure = new Procedure();

        procedure.setId(UUID.randomUUID().toString());
        procedure.setModifiedAt(new Date());
        procedure.setName("My fancy procedure name");
        procedure.setDescription("My fancy procedure description");
        procedure.setSteps(steps);

        return procedure;
    }
}
