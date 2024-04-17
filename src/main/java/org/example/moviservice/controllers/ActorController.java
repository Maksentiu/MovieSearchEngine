package org.example.moviservice.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.moviservice.dto.ActorDto;
import org.example.moviservice.services.ActorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/actors")
public class ActorController {

    private final ActorService actorService;

    @GetMapping
    public ResponseEntity<List<ActorDto>> getAllActors() {
        log.info("Trying GET all actors");
        List<ActorDto> actors = actorService.getAllActors();
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDto> getActorById(@PathVariable Long id) {
        log.info("Trying GET actor by id");
        ActorDto actorDto = actorService.getActorById(id);
        return new ResponseEntity<>(actorDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ActorDto> createActor(@RequestBody ActorDto actorDto) {
        log.info("Trying POST actor");
        ActorDto createdActor = actorService.createActor(actorDto);
        return new ResponseEntity<>(createdActor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActorDto> updateActor(@PathVariable Long id, @RequestBody ActorDto actorDto) {
        log.info("Trying PUT actor");
        ActorDto updatedActor = actorService.updateActor(id, actorDto);
        return new ResponseEntity<>(updatedActor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        log.info("Trying DEL actor");
        actorService.deleteActor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}