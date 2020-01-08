package com.logreposit.denkovi.denkovirelayapi.rest.mapper;
import static org.assertj.core.api.Assertions.assertThat;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.RelayOffTask;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.RelayOnTask;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.SleepTask;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Step;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.ProcedureRetrievalDto;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.RelayOffTaskDto;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.RelayOnTaskDto;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.SleepTaskDto;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.StepDto;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Procedure;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.ProcedureCreationDto;
import com.remondis.remap.Mapper;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

public class ProcedureMapperTests {

  private ProcedureMapper procedureMapper;

  @Before
  public void setUp() {
    this.procedureMapper = new ProcedureMapper();
  }

  @Test
  public void testFromCreationDtoToEntity() {
    Mapper<ProcedureCreationDto, Procedure> creationDtoToEntityMapper = this.procedureMapper.procedureDtoToEntityMapper();

    RelayOnTaskDto taskDto1 = new RelayOnTaskDto();
    taskDto1.setRelayNumber(5);

    StepDto<RelayOnTaskDto> stepDto1 = new StepDto<>();
    stepDto1.setOrder(100);
    stepDto1.setName("Step 1");
    stepDto1.setDescription("Description of Step 1");
    stepDto1.setTask(taskDto1);

    RelayOffTaskDto taskDto2 = new RelayOffTaskDto();
    taskDto2.setRelayNumber(6);

    StepDto<RelayOffTaskDto> stepDto2 = new StepDto<>();
    stepDto2.setOrder(200);
    stepDto2.setName("Step 2");
    stepDto2.setDescription("Description of Step 2");
    stepDto2.setTask(taskDto2);

    SleepTaskDto taskDto3 = new SleepTaskDto();
    taskDto3.setMilliseconds(1234);

    StepDto<SleepTaskDto> stepDto3 = new StepDto<>();
    stepDto3.setOrder(300);
    stepDto3.setName("Step 3");
    stepDto3.setDescription("Description of Step 3");
    stepDto3.setTask(taskDto3);

    ProcedureCreationDto procedureCreationDto = new ProcedureCreationDto();
    procedureCreationDto.setName("Some Procedure Name");
    procedureCreationDto.setDescription("Some Procedure Description");
    procedureCreationDto.setSteps(Arrays.asList(stepDto1, stepDto2, stepDto3));

    Procedure procedure = creationDtoToEntityMapper.map(procedureCreationDto);

    assertThat(procedure).isNotNull();
    assertThat(procedure.getId()).isNull();
    assertThat(procedure.getModifiedAt()).isNull();
    assertThat(procedure.getName()).isEqualTo("Some Procedure Name");
    assertThat(procedure.getDescription()).isEqualTo("Some Procedure Description");
    assertThat(procedure.getSteps()).hasSize(3);

    assertThat(procedure.getSteps().get(0).getName()).isEqualTo("Step 1");
    assertThat(procedure.getSteps().get(0).getDescription()).isEqualTo("Description of Step 1");
    assertThat(procedure.getSteps().get(0).getOrder()).isEqualTo(100);
    assertThat(procedure.getSteps().get(0).getTask()).isInstanceOf(RelayOnTask.class);
    assertThat(((RelayOnTask) procedure.getSteps().get(0).getTask()).getRelayNumber()).isEqualTo(5);

    assertThat(procedure.getSteps().get(1).getName()).isEqualTo("Step 2");
    assertThat(procedure.getSteps().get(1).getDescription()).isEqualTo("Description of Step 2");
    assertThat(procedure.getSteps().get(1).getOrder()).isEqualTo(200);
    assertThat(procedure.getSteps().get(1).getTask()).isInstanceOf(RelayOffTask.class);
    assertThat(((RelayOffTask) procedure.getSteps().get(1).getTask()).getRelayNumber()).isEqualTo(6);

    assertThat(procedure.getSteps().get(2).getName()).isEqualTo("Step 3");
    assertThat(procedure.getSteps().get(2).getDescription()).isEqualTo("Description of Step 3");
    assertThat(procedure.getSteps().get(2).getOrder()).isEqualTo(300);
    assertThat(procedure.getSteps().get(2).getTask()).isInstanceOf(SleepTask.class);
    assertThat(((SleepTask) procedure.getSteps().get(2).getTask()).getMilliseconds()).isEqualTo(1234);
  }

  @Test
  public void testFromEntityToRetrievalDtoMapper() {
    Mapper<Procedure, ProcedureRetrievalDto> entityToRetrievalDtoMapper = this.procedureMapper.procedureEntityToDtoMapper();

    RelayOnTask task1 = new RelayOnTask();
    task1.setRelayNumber(5);

    Step<RelayOnTask> step1 = new Step<>();
    step1.setOrder(100);
    step1.setName("Step 1");
    step1.setDescription("Description of Step 1");
    step1.setTask(task1);

    RelayOffTask task2 = new RelayOffTask();
    task2.setRelayNumber(6);

    Step<RelayOffTask> step2 = new Step<>();
    step2.setOrder(200);
    step2.setName("Step 2");
    step2.setDescription("Description of Step 2");
    step2.setTask(task2);

    SleepTask task3 = new SleepTask();
    task3.setMilliseconds(1234);

    Step<SleepTask> step3 = new Step<>();
    step3.setOrder(300);
    step3.setName("Step 3");
    step3.setDescription("Description of Step 3");
    step3.setTask(task3);

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.MINUTE, -5);

    Procedure procedure = new Procedure();
    procedure.setId("23575928-18c3-48a1-8f8c-18c4ebb8430d");
    procedure.setName("Some Procedure Name");
    procedure.setDescription("Some Procedure Description");
    procedure.setSteps(Arrays.asList(step1, step2, step3));
    procedure.setCreatedAt(calendar.getTime());
    procedure.setModifiedAt(new Date());

    ProcedureRetrievalDto procedureRetrievalDto = entityToRetrievalDtoMapper.map(procedure);

    assertThat(procedureRetrievalDto).isNotNull();
    assertThat(procedureRetrievalDto.getId()).isEqualTo("23575928-18c3-48a1-8f8c-18c4ebb8430d");
    assertThat(procedureRetrievalDto.getCreatedAt()).isEqualTo(procedure.getCreatedAt());
    assertThat(procedureRetrievalDto.getModifiedAt()).isEqualTo(procedure.getModifiedAt());
    assertThat(procedureRetrievalDto.getName()).isEqualTo("Some Procedure Name");
    assertThat(procedureRetrievalDto.getDescription()).isEqualTo("Some Procedure Description");
    assertThat(procedureRetrievalDto.getSteps()).hasSize(3);

    assertThat(procedureRetrievalDto.getSteps().get(0).getName()).isEqualTo("Step 1");
    assertThat(procedureRetrievalDto.getSteps().get(0).getDescription()).isEqualTo("Description of Step 1");
    assertThat(procedureRetrievalDto.getSteps().get(0).getOrder()).isEqualTo(100);
    assertThat(procedureRetrievalDto.getSteps().get(0).getTask()).isInstanceOf(RelayOnTaskDto.class);
    assertThat(((RelayOnTaskDto) procedureRetrievalDto.getSteps().get(0).getTask()).getRelayNumber()).isEqualTo(5);

    assertThat(procedureRetrievalDto.getSteps().get(1).getName()).isEqualTo("Step 2");
    assertThat(procedureRetrievalDto.getSteps().get(1).getDescription()).isEqualTo("Description of Step 2");
    assertThat(procedureRetrievalDto.getSteps().get(1).getOrder()).isEqualTo(200);
    assertThat(procedureRetrievalDto.getSteps().get(1).getTask()).isInstanceOf(RelayOffTaskDto.class);
    assertThat(((RelayOffTaskDto) procedureRetrievalDto.getSteps().get(1).getTask()).getRelayNumber()).isEqualTo(6);

    assertThat(procedureRetrievalDto.getSteps().get(2).getName()).isEqualTo("Step 3");
    assertThat(procedureRetrievalDto.getSteps().get(2).getDescription()).isEqualTo("Description of Step 3");
    assertThat(procedureRetrievalDto.getSteps().get(2).getOrder()).isEqualTo(300);
    assertThat(procedureRetrievalDto.getSteps().get(2).getTask()).isInstanceOf(SleepTaskDto.class);
    assertThat(((SleepTaskDto) procedureRetrievalDto.getSteps().get(2).getTask()).getMilliseconds()).isEqualTo(1234);
  }
}
