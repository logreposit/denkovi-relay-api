package com.logreposit.denkovi.denkovirelayapi.rest.controllers;

import com.logreposit.denkovi.denkovirelayapi.rest.dtos.Relay;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.RelayState;
import com.logreposit.denkovi.denkovirelayapi.rest.dtos.RelayUpdateDto;
import com.logreposit.denkovi.denkovirelayapi.services.DenkoviRelayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/api/relays")
@Validated
public class DenkoviRelayController
{
    private final DenkoviRelayService denkoviRelayService;

    public DenkoviRelayController(DenkoviRelayService denkoviRelayService)
    {
        this.denkoviRelayService = denkoviRelayService;
    }

    @CrossOrigin // TODO
    @GetMapping
    public ResponseEntity<List<Relay>> getAll()
    {
        List<Relay> relays = this.denkoviRelayService.get();

        return new ResponseEntity<>(relays, HttpStatus.OK);
    }

    @CrossOrigin // TODO
    @PostMapping
    public ResponseEntity<List<Relay>> setAll(@RequestBody @Valid List<Relay> relays)
    {
        this.denkoviRelayService.set(relays);

        List<Relay> relayStates = this.denkoviRelayService.get();

        return new ResponseEntity<>(relayStates, HttpStatus.OK);
    }

    @CrossOrigin // TODO
    @GetMapping("/{relayNumber:[1-9]|1[0-6]}")
    public ResponseEntity<Relay> get(@PathVariable("relayNumber") int relayNumber)
    {
        Relay relay = this.denkoviRelayService.get(relayNumber);

        return new ResponseEntity<>(relay, HttpStatus.OK);
    }

    @CrossOrigin // TODO
    @PutMapping("/{relayNumber:[1-9]|1[0-6]}")
    public ResponseEntity<Relay> update(@PathVariable("relayNumber") int relayNumber,
        @RequestBody @Valid RelayUpdateDto relayUpdateDto)
    {
        this.denkoviRelayService.update(relayNumber, relayUpdateDto);

        Relay relay = this.denkoviRelayService.get(relayNumber);

        return new ResponseEntity<>(relay, HttpStatus.OK);
    }

    @CrossOrigin // TODO
    @PostMapping("/{relayNumber:[1-9]|1[0-6]}/on")
    public ResponseEntity<Relay> on(@PathVariable("relayNumber") int relayNumber)
    {
        this.denkoviRelayService.set(relayNumber, RelayState.ON);

        Relay relay = this.denkoviRelayService.get(relayNumber);

        return new ResponseEntity<>(relay, HttpStatus.OK);
    }

    @CrossOrigin // TODO
    @PostMapping("/{relayNumber:[1-9]|1[0-6]}/off")
    public ResponseEntity<Relay> off(@PathVariable("relayNumber") int relayNumber)
    {
        this.denkoviRelayService.set(relayNumber, RelayState.OFF);

        Relay relay = this.denkoviRelayService.get(relayNumber);

        return new ResponseEntity<>(relay, HttpStatus.OK);
    }

    @CrossOrigin // TODO
    @PostMapping("/{relayNumber:[1-9]|1[0-6]}/pulse-signal/on-off")
    public ResponseEntity<Relay> pulseSignalOnOff(@PathVariable("relayNumber") int relayNumber)
    {
        this.denkoviRelayService.set(relayNumber, RelayState.ON);
        this.denkoviRelayService.set(relayNumber, RelayState.OFF);

        Relay relay = this.denkoviRelayService.get(relayNumber);

        return new ResponseEntity<>(relay, HttpStatus.OK);
    }

    @CrossOrigin // TODO
    @PostMapping("/{relayNumber:[1-9]|1[0-6]}/pulse-signal/off-on")
    public ResponseEntity<Relay> pulseSignalOffOn(@PathVariable("relayNumber") int relayNumber)
    {
        this.denkoviRelayService.set(relayNumber, RelayState.OFF);
        this.denkoviRelayService.set(relayNumber, RelayState.ON);

        Relay relay = this.denkoviRelayService.get(relayNumber);

        return new ResponseEntity<>(relay, HttpStatus.OK);
    }
}
