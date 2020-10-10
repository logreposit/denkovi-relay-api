package com.logreposit.denkovi.denkovirelayapi.rest.mapper;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Procedure;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.RelayOffTask;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.RelayOnTask;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.SleepTask;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Step;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.ProcedureCreationDto;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.ProcedureRetrievalDto;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.RelayOffTaskDto;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.RelayOnTaskDto;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.SleepTaskDto;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.StepDto;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcedureMapper {

  @Bean
  Mapper<ProcedureCreationDto, Procedure> procedureDtoToEntityMapper() {
    Mapper<RelayOnTaskDto, RelayOnTask> relayOnTaskDtoToEntityMapper = Mapping
        .from(RelayOnTaskDto.class).to(RelayOnTask.class).mapper();
    Mapper<RelayOffTaskDto, RelayOffTask> relayOffTaskDtoToEntityMapper = Mapping
        .from(RelayOffTaskDto.class).to(RelayOffTask.class).mapper();
    Mapper<SleepTaskDto, SleepTask> sleepTaskDtoToEntityMapper = Mapping.from(SleepTaskDto.class)
        .to(SleepTask.class).mapper();

    return Mapping
        .from(ProcedureCreationDto.class)
        .to(Procedure.class)
        .useMapper(
            Mapping
                .from(StepDto.class)
                .to(Step.class)
                .replace(StepDto::getTask, Step::getTask)
                .with(o -> {
                  if (o instanceof RelayOnTaskDto) {
                    return relayOnTaskDtoToEntityMapper.map((RelayOnTaskDto) o);
                  } else if (o instanceof RelayOffTaskDto) {
                    return relayOffTaskDtoToEntityMapper.map((RelayOffTaskDto) o);
                  } else if (o instanceof SleepTaskDto) {
                    return sleepTaskDtoToEntityMapper.map((SleepTaskDto) o);
                  } else {
                    throw new UnsupportedOperationException(
                        "Cannot map object to instance of Task");
                  }
                }).mapper())
        .omitInDestination(Procedure::getId)
        .omitInDestination(Procedure::getCreatedAt)
        .omitInDestination(Procedure::getModifiedAt)
        .mapper();
  }

  @Bean
  Mapper<Procedure, ProcedureRetrievalDto> procedureEntityToDtoMapper() {
    Mapper<RelayOnTask, RelayOnTaskDto> relayOnTaskEntityToDtoMapper = Mapping
        .from(RelayOnTask.class).to(RelayOnTaskDto.class).mapper();
    Mapper<RelayOffTask, RelayOffTaskDto> relayOffTaskEntityToDtoMapper = Mapping
        .from(RelayOffTask.class).to(RelayOffTaskDto.class).mapper();
    Mapper<SleepTask, SleepTaskDto> sleepTaskEntityToDtoMapper = Mapping.from(SleepTask.class)
        .to(SleepTaskDto.class).mapper();

    return Mapping
        .from(Procedure.class)
        .to(ProcedureRetrievalDto.class)
        .useMapper(Mapping
            .from(Step.class)
            .to(StepDto.class)
            .replace(Step::getTask, StepDto::getTask)
            .with(o -> {
              if (o instanceof RelayOnTask) {
                return relayOnTaskEntityToDtoMapper.map((RelayOnTask) o);
              } else if (o instanceof RelayOffTask) {
                return relayOffTaskEntityToDtoMapper.map((RelayOffTask) o);
              } else if (o instanceof SleepTask) {
                return sleepTaskEntityToDtoMapper.map((SleepTask) o);
              } else {
                throw new UnsupportedOperationException(
                    "Cannot map object to instance of Task");
              }
            }).mapper())
        .mapper();
  }
}
