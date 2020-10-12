package com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure;

import java.util.List;
import lombok.Data;

@Data
public class ProcedureCreationDto {
  private String name;
  private String description;
  private List<StepDto> steps;
}
