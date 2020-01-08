package com.logreposit.denkovi.denkovirelayapi.rest.controllers;

import com.logreposit.denkovi.denkovirelayapi.services.procedure.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/procedures")
@Validated
public class ProcedureController
{
  private final ProcedureService procedureService;

  @Autowired
  public ProcedureController(ProcedureService procedureService)
  {
    this.procedureService = procedureService;
  }

  // TODO DoM: Add controller to add, delete, play procedures
}
