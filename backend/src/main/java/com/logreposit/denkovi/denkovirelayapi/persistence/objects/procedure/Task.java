package com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "action")
@JsonSubTypes({
    @JsonSubTypes.Type(value = RelayOnTask.class, name = "RELAY_ON"),
    @JsonSubTypes.Type(value = RelayOffTask.class, name = "RELAY_OFF"),
    @JsonSubTypes.Type(value = SleepTask.class, name = "SLEEP"),
})
//public abstract class Task {
public class Task {
  private Action action;

  public Task() {
  } // needed for remap

  public Task(Action action)
  {
    this.action = action;
  }
}
