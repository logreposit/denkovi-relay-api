package com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Action;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RelayOffTaskDto extends TaskDto
{
  private int relayNumber;

  public RelayOffTaskDto()
  {
    this.setAction(Action.RELAY_OFF);
  }
}
