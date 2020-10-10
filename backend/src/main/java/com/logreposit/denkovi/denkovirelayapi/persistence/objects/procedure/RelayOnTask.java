package com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RelayOnTask extends Task {
  private int relayNumber;

  public RelayOnTask()
  {
    super(Action.RELAY_ON);
  }
}
