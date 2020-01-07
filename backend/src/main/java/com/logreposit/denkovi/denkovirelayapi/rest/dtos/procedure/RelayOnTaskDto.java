package com.logreposit.denkovi.denkovirelayapi.rest.dtos.procedure;

import com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure.Action;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RelayOnTaskDto extends TaskDto
{
  private int relayNumber;

  public RelayOnTaskDto()
  {
    this.setAction(Action.RELAY_ON);
  }
}
