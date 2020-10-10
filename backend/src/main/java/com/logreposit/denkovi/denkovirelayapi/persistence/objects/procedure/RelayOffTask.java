package com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RelayOffTask extends Task {
  private int relayNumber;

  public RelayOffTask()
  {
    super(Action.RELAY_OFF);
  }
}
