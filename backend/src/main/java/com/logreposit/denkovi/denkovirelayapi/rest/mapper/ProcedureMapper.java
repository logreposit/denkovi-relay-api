package com.logreposit.denkovi.denkovirelayapi.rest.mapper;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Procedure;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Step;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Task;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.ProcedureCreationDto;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.StepDto;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.TaskDto;
import com.remondis.remap.Mapper;
import com.remondis.remap.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcedureMapper {

  @Bean
  Mapper<ProcedureCreationDto, Procedure> creationDtoToEntityMapper()
  {
    return Mapping
        .from(ProcedureCreationDto.class)
        .to(Procedure.class)
        .omitInDestination(Procedure::getId)
        .omitInDestination(Procedure::getModifiedAt)
        .useMapper(Mapping.from(StepDto.class).to(Step.class).useMapper(Mapping.from(TaskDto.class).to(Task.class).mapper()).mapper())
        .mapper();
  }

}
