package com.logreposit.denkovi.denkovirelayapi.rest.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelayUpdateDto
{
  private String name;
  private String description;
  private List<String> tags;
}
