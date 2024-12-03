package com.journey_back.controller;

import com.journey_back.infra.exception.ValidationError;
import com.journey_back.model.LinkModel;
import com.journey_back.request.LinkRequest;
import com.journey_back.service.LinksService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/links")
public class LinkController {

    @Autowired
    private LinksService linksService;

    // Construtor
    public LinkController(LinksService service) {
        this.linksService = service;
    }

    // Listar Links
    @GetMapping("/{tripId}")
    public ResponseEntity<List<LinkModel>> getLinks(@PathVariable Integer tripId) {
        return ResponseEntity.ok().body(linksService.getLinks(tripId));
    }

    // Cadastrar link
    @PostMapping
    public ResponseEntity<LinkModel> registerLink(@RequestBody @Valid LinkRequest linkRequest, @PathVariable Integer tripId) {
        return ResponseEntity.status(201).body(linksService.registerLink(linkRequest, tripId));
    }

    // Atualizar link
    @PutMapping("/{id}")
    public ResponseEntity<LinkModel> updateLink(@RequestBody @Valid LinkRequest linkRequest, @PathVariable Integer id) {
        return ResponseEntity.status(201).body(linksService.updateLink(id, linkRequest));
    }

    // Deletar Link
    @DeleteMapping("/{id}")
    public ResponseEntity deleteLink(@PathVariable Integer id) {
        var exists = linksService.deleteLink(id);
        if (!exists) {
            throw new ValidationError("Link não encontrado");
        } else {
            return ResponseEntity.status(204).build();
        }
    }

}
