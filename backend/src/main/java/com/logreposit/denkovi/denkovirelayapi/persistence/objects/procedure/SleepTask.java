package com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SleepTask extends Task {
  private int milliseconds;

  public SleepTask()
  {
    super(Action.SLEEP);
  }
}
