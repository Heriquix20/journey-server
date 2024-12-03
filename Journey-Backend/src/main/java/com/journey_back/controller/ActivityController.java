package com.journey_back.controller;


import com.journey_back.infra.exception.ValidationError;
import com.journey_back.model.ActivityModel;
import com.journey_back.request.ActivityRequest;
import com.journey_back.service.ActivitiesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivitiesService activitiesService;

    // Construtor
    public ActivityController(ActivitiesService service) {
        this.activitiesService = service;
    }

    // Listar atividades
    @GetMapping("/{tripId}")
    public ResponseEntity<List<ActivityModel>> getActivities(@PathVariable Integer tripId) {
        return ResponseEntity.ok().body(activitiesService.getActivities(tripId));
    }

    // Cadastrar atividade
    @PostMapping
    public ResponseEntity<ActivityModel> registerActivity(@RequestBody @Valid ActivityRequest activityRequest, @PathVariable Integer tripId) {
        return ResponseEntity.status(201).body(activitiesService.registerActivity(activityRequest, tripId));
    }

    // Atualizar atividade
    @PutMapping("/{id}")
    public ResponseEntity<ActivityModel> updateActivity(@PathVariable Integer id, @RequestBody @Valid ActivityRequest activityRequest) {
        return ResponseEntity.status(201).body(activitiesService.updateActivity(id, activityRequest));
    }


    // Deletar atividade
    @DeleteMapping("/{id}")
    public ResponseEntity deleteActivity(@PathVariable Integer id) {
        var exists = activitiesService.deleteActivity(id);
        if (!exists) {
            throw new ValidationError("Esta atividade nao existe");
        } else {
            return ResponseEntity.status(204).build();
        }
    }

}
