package com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Action;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.RelayOffTask;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.RelayOnTask;
import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.SleepTask;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "action")
@JsonSubTypes({
    @JsonSubTypes.Type(value = RelayOnTaskDto.class, name = "RELAY_ON"),
    @JsonSubTypes.Type(value = RelayOffTaskDto.class, name = "RELAY_OFF"),
    @JsonSubTypes.Type(value = SleepTaskDto.class, name = "SLEEP"),
})
public abstract class TaskDto {
  private Action action;
}
