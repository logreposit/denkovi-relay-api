package com.logreposit.denkovi.denkovirelayapi.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relay
{
    @Min(1)
    @Max(16)
    private int number;

    @NotNull
    private RelayState state;
}
