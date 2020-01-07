package com.logreposit.denkovi.denkovirelayapi.rest.mapper;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Procedure;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.ProcedureCreationDto;
import com.remondis.remap.Mapper;
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
    Mapper<ProcedureCreationDto, Procedure> creationDtoToEntityMapper = this.procedureMapper.creationDtoToEntityMapper();

    // TODO DoM
  }
}
