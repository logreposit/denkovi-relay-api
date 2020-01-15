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
    Procedure procedure = this.procedureDtoToEntityMapper.map(procedureCreationDto);
    Procedure savedProcedure = this.procedureService.create(procedure);
    ProcedureRetrievalDto procedureRetrievalDto = this.procedureEntityToDtoMapper.map(savedProcedure);

    return new ResponseEntity<>(procedureRetrievalDto, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProcedureRetrievalDto> get(@PathVariable("id") String id)
  {
    Procedure procedure = this.procedureService.retrieve(id);
    ProcedureRetrievalDto procedureRetrievalDto = this.procedureEntityToDtoMapper.map(procedure);

    return new ResponseEntity<>(procedureRetrievalDto, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProcedureRetrievalDto> update(@PathVariable("id") String id,
      @RequestBody @Valid ProcedureCreationDto procedureCreationDto) {
    Procedure procedure = this.procedureDtoToEntityMapper.map(procedureCreationDto);
    Procedure updatedProcedure = this.procedureService.update(id, procedure);
    ProcedureRetrievalDto procedureRetrievalDto = this.procedureEntityToDtoMapper.map(updatedProcedure);

    return new ResponseEntity<>(procedureRetrievalDto, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ProcedureRetrievalDto> delete(@PathVariable("id") String id)
  {
    this.procedureService.delete(id);

    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  @PostMapping("/{id}/actions/play")
  public ResponseEntity<Void> play(@PathVariable("id") String id) throws InterruptedException {
    this.procedureService.play(id);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
