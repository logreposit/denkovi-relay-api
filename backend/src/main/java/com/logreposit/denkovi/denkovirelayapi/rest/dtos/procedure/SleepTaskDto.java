package com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Action;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SleepTaskDto extends TaskDto
{
  private int milliseconds;

  public SleepTaskDto()
  {
    this.setAction(Action.SLEEP);
  }
}
