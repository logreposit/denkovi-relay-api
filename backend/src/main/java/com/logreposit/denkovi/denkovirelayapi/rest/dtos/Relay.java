package com.logreposit.denkovi.denkovirelayapi.rest.dtos;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Relay
{
    @Min(1)
    @Max(16)
    private int number;

    @NotNull
    private RelayState state;

    private String name;
    private String description;
    private List<String> tags;

    public Relay(int number, RelayState relayState) {
        this.number = number;
        this.state = relayState;
    }
}
