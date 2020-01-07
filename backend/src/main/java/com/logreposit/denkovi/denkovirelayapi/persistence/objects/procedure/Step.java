package com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure;

import lombok.Data;

@Data
public class Step {
  private int order;
  private String name;
  private String description;
  private Task task;
}
