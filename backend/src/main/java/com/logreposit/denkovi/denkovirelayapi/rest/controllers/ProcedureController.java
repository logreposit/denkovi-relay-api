package com.logreposit.denkovi.denkovirelayapi.rest.controllers;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Procedure;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.ProcedureCreationDto;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure.ProcedureRetrievalDto;
import com.logreposit.denkovi.denkovirelayapi.services.procedure.ProcedureService;
import com.remondis.remap.Mapper;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/procedures")
@Validated
public class ProcedureController
{
  private final ProcedureService procedureService;
  private final Mapper<ProcedureCreationDto, Procedure> procedureDtoToEntityMapper;
  private final Mapper<Procedure, ProcedureRetrievalDto> procedureEntityToDtoMapper;

  @Autowired
  public ProcedureController(ProcedureService procedureService,
      Mapper<ProcedureCreationDto, Procedure> procedureDtoToEntityMapper,
      Mapper<Procedure, ProcedureRetrievalDto> procedureEntityToDtoMapper)
  {
    this.procedureService = procedureService;
    this.procedureDtoToEntityMapper = procedureDtoToEntityMapper;
    this.procedureEntityToDtoMapper = procedureEntityToDtoMapper;
  }

  @PostMapping
  public ResponseEntity<ProcedureRetrievalDto> create(@RequestBody @Valid ProcedureCreationDto procedureCreationDto)
  {
    return new ResponseEntity<>(null, HttpStatus.CREATED); // TODO
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProcedureRetrievalDto> get(@PathVariable("id") String id)
  {
    return new ResponseEntity<>(null, HttpStatus.OK); // TODO
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProcedureRetrievalDto> update(@PathVariable("id") String id,
      @RequestBody @Valid ProcedureCreationDto procedureCreationDto)
  {
    return new ResponseEntity<>(null, HttpStatus.OK); // TODO
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ProcedureRetrievalDto> delete(@PathVariable("id") String id)
  {
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT); // TODO
  }

  @PostMapping("/{id}")
  public ResponseEntity<ProcedureRetrievalDto> play(@PathVariable("id") String id)
  {
    return new ResponseEntity<>(null, HttpStatus.OK); // TODO
  }

  // TODO DoM: Add controller to add, delete, play procedures
}
