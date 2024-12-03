package com.journey_back.controller;

import com.journey_back.infra.exception.ValidationError;
import com.journey_back.model.TripModel;
import com.journey_back.request.TripRequest;
import com.journey_back.service.TripService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    // Construtor
    public TripController(TripService service) {
        this.tripService = service;
    }

    // Mostrar viagens
    @GetMapping
    public ResponseEntity<List<TripModel>> getTrips() {
        return ResponseEntity.ok().body(tripService.getTripList());
    }

    // Cadastrar viagem
    @PostMapping
    public ResponseEntity<TripModel> registerTrip(@RequestBody @Valid TripRequest tripRequest) {
        return ResponseEntity.status(201).body(tripService.registerTrip(tripRequest));
    }

    // Detalhes de uma viagem
    @GetMapping("/{id}")
    public ResponseEntity<TripModel> getTripDetails(@PathVariable Integer id) {
        return ResponseEntity.ok().body(tripService.getTripDetails(id));
    }

    // Atualizar uma viagem
    @PutMapping("/{id}")
    public ResponseEntity<TripModel> updateTrip(@PathVariable Integer id, @RequestBody @Valid TripRequest payload) {
        return ResponseEntity.status(201).build();
    }

    // Deletar uma viagem
    @DeleteMapping("/{id}")
    public ResponseEntity<TripModel> deleteTrip(@PathVariable Integer id) {
        var exists = tripService.deleteTrip(id);
        if (!exists) {
            throw new ValidationError("Esta viagem nao existe");
        } else {
            return ResponseEntity.status(204).build();
        }
    }

}
