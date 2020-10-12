package com.logreposit.denkovi.denkovirelayapi.persistence.objects.procedure;

import java.util.Date;
import java.util.List;
import lombok.Data;
import org.dizitart.no2.objects.Id;

@Data
public class Procedure {
  @Id
  private String id;

  private String name;
  private String description;
  private Date createdAt;
  private Date modifiedAt;

  private List<Step> steps;
}
