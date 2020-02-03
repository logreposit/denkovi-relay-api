package com.logreposit.denkovi.denkovirelayapi.services.procedure;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Procedure;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.RelayOffTask;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.RelayOnTask;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.SleepTask;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Step;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Task;
import com.logreposit.denkovi.denkovirelayapi.persistence.repositories.ProcedureRepository;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.Relay;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.RelayState;
import com.logreposit.denkovi.denkovirelayapi.services.DenkoviRelayService;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProcedureService {

  private final ProcedureRepository procedureRepository;
  private final DenkoviRelayService denkoviRelayService;
  private final Executor executor;
  private final SimpMessagingTemplate messagingTemplate;

  @Autowired
  public ProcedureService(ProcedureRepository procedureRepository,
      DenkoviRelayService denkoviRelayService,
      @Qualifier("simpleAsyncExecutor") Executor executor,
      SimpMessagingTemplate messagingTemplate)
  {
    this.procedureRepository = procedureRepository;
    this.denkoviRelayService = denkoviRelayService;
    this.executor = executor;
    this.messagingTemplate = messagingTemplate;
  }

  public Procedure create(Procedure procedure) {
    procedure.setId(UUID.randomUUID().toString());

    Procedure savedProcedure = this.procedureRepository.save(procedure);

    return savedProcedure;
  }

  public List<Procedure> list() {
    return this.procedureRepository.findAll();
  }

  public Procedure retrieve(String procedureId) {
    Optional<Procedure> procedureOptional = this.procedureRepository.get(procedureId);

    if (!procedureOptional.isPresent()) {
      throw new IllegalArgumentException("procedure with given ID not existent."); // TODO
    }

    return procedureOptional.get();
  }

  public Procedure update(String procedureId, Procedure procedure) {
    procedure.setId(procedureId);

    Procedure savedProcedure = this.procedureRepository.save(procedure);

    return savedProcedure;
  }

  public void delete(String procedureId) {
    this.retrieve(procedureId);
    this.procedureRepository.delete(procedureId);
  }

  public void play(String procedureId) {
    Procedure procedure = this.retrieve(procedureId);
    List<Step> steps = procedure.getSteps();

    procedure.getSteps().sort(Comparator.comparing(Step::getOrder));

    // TODO: refactor
    this.executor.execute(() -> {
      for (Step step : steps)
      {
        try {
          this.executeTask(step.getTask());
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }

      this.sendProcedureCompletedMessage(procedureId);
    });
  }

  // Just kept here for simplicity, should be extracted in a own service / other architecture if
  // it grows heavily.
  private void executeTask(Task task) throws InterruptedException {
    if (task instanceof RelayOnTask)
    {
      this.denkoviRelayService.set(((RelayOnTask) task).getRelayNumber(), RelayState.ON);
    }
    else if (task instanceof RelayOffTask)
    {
      this.denkoviRelayService.set(((RelayOffTask) task).getRelayNumber(), RelayState.OFF);
    }
    else if (task instanceof SleepTask)
    {
      Thread.sleep(((SleepTask) task).getMilliseconds());
    }
    else
    {
      throw new RuntimeException("not yet implemented");
    }
  }

  private void sendProcedureCompletedMessage(String id) {
    Map<String, String> procedureCompletedMessagePayload = new HashMap<>();

    procedureCompletedMessagePayload.put("id", id);

    this.messagingTemplate.convertAndSend("/procedures/completed", procedureCompletedMessagePayload);
  }
}
