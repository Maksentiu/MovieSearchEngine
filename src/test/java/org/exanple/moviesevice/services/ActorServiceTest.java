package org.exanple.moviesevice.services;

import org.example.moviservice.dto.ActorDto;
import org.example.moviservice.model.Actor;
import org.example.moviservice.repositories.ActorRepository;
import org.example.moviservice.services.ActorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorService actorService;

    public ActorServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllActors() {
        // Создаем тестовые данные
        Actor actor = new Actor();
        actor.setId(1L);
        actor.setName("John Doe");

        // Устанавливаем поведение мокитированного репозитория
        when(actorRepository.findAll()).thenReturn(Collections.singletonList(actor));

        // Вызываем метод, который мы тестируем
        List<ActorDto> actorDtos = actorService.getAllActors();

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(1, actorDtos.size());
        assertEquals("John Doe", actorDtos.get(0).getName());
    }

    @Test
    void testGetActorById() {
        // Создаем тестовые данные
        Actor actor = new Actor();
        actor.setId(1L);
        actor.setName("John Doe");

        // Устанавливаем поведение мокитированного репозитория
        when(actorRepository.findById(1L)).thenReturn(Optional.of(actor));

        // Вызываем метод, который мы тестируем
        ActorDto actorDto = actorService.getActorById(1L);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals("John Doe", actorDto.getName());
    }
    @Test
    void testCreateActor() {
        // Создаем тестовые данные
        ActorDto actorDto = new ActorDto();
        actorDto.setId(1L);
        actorDto.setName("John Doe");

        Actor actor = new Actor();
        actor.setId(1L);
        actor.setName("John Doe");

        // Устанавливаем поведение мокитированного репозитория
        when(actorRepository.save(any(Actor.class))).thenReturn(actor);

        // Вызываем метод, который мы тестируем
        ActorDto createdActorDto = actorService.createActor(actorDto);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals("John Doe", createdActorDto.getName());
    }

    @Test
    void testUpdateActor() {
        // Создаем тестовые данные
        ActorDto actorDto = new ActorDto();
        actorDto.setId(1L);
        actorDto.setName("John Doe");

        Actor existingActor = new Actor();
        existingActor.setId(1L);
        existingActor.setName("Existing Actor");

        // Устанавливаем поведение мокитированного репозитория
        when(actorRepository.findById(1L)).thenReturn(Optional.of(existingActor));
        when(actorRepository.save(any(Actor.class))).thenReturn(existingActor);

        // Вызываем метод, который мы тестируем
        ActorDto updatedActorDto = actorService.updateActor(1L, actorDto);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals("John Doe", updatedActorDto.getName());
    }

    @Test
    void testDeleteActor() {
        // Создаем мок объекта Actor
        Actor actor = mock(Actor.class);

        // Устанавливаем поведение мок объекта
        doNothing().when(actor).removeMovies();
        when(actorRepository.findById(1L)).thenReturn(Optional.of(actor));

        // Вызываем метод, который мы тестируем
        actorService.deleteActor(1L);

        // Проверяем, что метод removeMovies и deleteById были вызваны
        verify(actor, times(1)).removeMovies();
        verify(actorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testActorToActorDto() {
        // Создаем тестового актера
        Actor actor = new Actor();
        actor.setId(1L);
        actor.setName("Test Actor");

        // Вызываем метод, который мы тестируем
        ActorDto actorDto = actorService.actorToActorDto(actor);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(1L, actorDto.getId());
        assertEquals("Test Actor", actorDto.getName());
    }

    @Test
    void testActorDtoToActor() {
        // Создаем тестовый DTO актера
        ActorDto actorDto = new ActorDto();
        actorDto.setId(1L);
        actorDto.setName("Test Actor");

        // Вызываем метод, который мы тестируем
        Actor actor = actorService.actorDtoToActor(actorDto);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(1L, actor.getId());
        assertEquals("Test Actor", actor.getName());
    }
}

