package com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StepDto {
  @NotNull
  private Integer order;

  @NotBlank
  private String name;

  private String description;
  private TaskDto task;
}
