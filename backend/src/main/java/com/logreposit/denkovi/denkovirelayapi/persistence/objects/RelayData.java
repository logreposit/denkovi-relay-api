package com.logreposit.denkovi.denkovirelayapi.persistence.objects;

import lombok.Data;
import org.dizitart.no2.objects.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class RelayData implements Serializable
{
    @Id
    private int number;

    private Date modifiedAt;
    private String name;
    private String description;
    private List<String> tags;
}
