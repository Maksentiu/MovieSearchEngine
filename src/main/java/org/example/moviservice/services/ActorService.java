package org.example.moviservice.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.moviservice.dto.ActorDto;
import org.example.moviservice.model.Movie;
import org.example.moviservice.repositories.ActorRepository;
import org.springframework.stereotype.Service;
import org.example.moviservice.model.Actor;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class ActorService {
    private final ActorRepository actorRepository;

    public List<ActorDto> getAllActors() {
        log.info("Trying find actors from database");
        List<Actor> actors = actorRepository.findAll();
        return actors.stream()
                .map(this::actorToActorDto)
                .toList();
    }

    public ActorDto getActorById(Long id) {
        log.info("Trying find actors by id from database");
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() ->
                new RuntimeException("Actor not found with id: " + id));
        return actorToActorDto(actor);
    }

    public ActorDto createActor(ActorDto actorDto) {
        log.info("Trying create actor");
        Actor actor = actorDtoToActor(actorDto);
        actor = actorRepository.save(actor);
        return actorToActorDto(actor);
    }

    public Actor actorExist(ActorDto actorDto) {
        log.info("Trying to check is actor exist");
        List<Actor> actors = actorRepository.findAll();
        for (Actor actor : actors) {
            if (actor.getName().equals(actorDto.getName())) {
                return actor;
            }
        }

        Actor newActor = new Actor();
        newActor.setName(actorDto.getName());
        return actorRepository.save(newActor);
    }

    public ActorDto updateActor(Long id, ActorDto actorDto) {
        log.info("Trying update actor");
        Actor existingActor = actorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actor " + id + " not found"));

        updateActorFromDto(actorDto, existingActor);
        existingActor.setId(id);
        existingActor = actorRepository.save(existingActor);
        return actorToActorDto(existingActor);
    }

    public void deleteActor(Long id) {
        log.info("Trying delete actor from database by id : {}", id);
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actor not found with id: " + id));

        actor.removeMovies();

        actorRepository.delete(actor);

        actorRepository.deleteById(id);
    }

    public ActorDto actorToActorDto(Actor actor) {
        ActorDto actorDto = new ActorDto();
        actorDto.setId(actor.getId());
        actorDto.setName(actor.getName());
        return actorDto;
    }

    public Actor actorDtoToActor(ActorDto actorDto) {
        Actor actor = new Actor();
        actor.setId(actorDto.getId());
        actor.setName(actorDto.getName());
        return actor;
    }

    public void updateActorFromDto(ActorDto actorDto, Actor actor) {
        actor.setName(actorDto.getName());
    }
}