package com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class ProcedureRetrievalDto {
  private String id;
  private Date createdAt;
  private Date modifiedAt;
  private String name;
  private String description;
  private List<StepDto> steps;
}
