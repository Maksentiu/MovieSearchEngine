package org.exanple.moviesevice.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.moviservice.controllers.ActorController;
import org.example.moviservice.dto.ActorDto;
import org.example.moviservice.services.ActorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActorControllerTest {

    private ActorController actorController;
    private ActorService actorService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        actorService = mock(ActorService.class);
        actorController = new ActorController(actorService);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllActors() {
        // Arrange
        List<ActorDto> mockActors = List.of(new ActorDto(), new ActorDto());
        when(actorService.getAllActors()).thenReturn(mockActors);

        // Act
        ResponseEntity<List<ActorDto>> responseEntity = actorController.getAllActors();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockActors, responseEntity.getBody());
    }

    @Test
    void testGetActorById() {
        // Arrange
        Long id = 1L;
        ActorDto mockActor = new ActorDto();
        when(actorService.getActorById(id)).thenReturn(mockActor);

        // Act
        ResponseEntity<ActorDto> responseEntity = actorController.getActorById(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockActor, responseEntity.getBody());
    }

    @Test
    void testCreateActor() throws Exception {
        // Arrange
        ActorDto actorDto = new ActorDto();
        actorDto.setName("Test Actor");
        String requestBody = objectMapper.writeValueAsString(actorDto);

        ActorDto mockCreatedActor = new ActorDto();
        when(actorService.createActor(actorDto)).thenReturn(mockCreatedActor);

        // Act
        ResponseEntity<ActorDto> responseEntity = actorController.createActor(actorDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockCreatedActor, responseEntity.getBody());
    }

    @Test
    void testUpdateActor() throws Exception {
        // Arrange
        Long id = 1L;
        ActorDto actorDto = new ActorDto();
        actorDto.setName("Updated Actor");
        String requestBody = objectMapper.writeValueAsString(actorDto);

        ActorDto mockUpdatedActor = new ActorDto();
        when(actorService.updateActor(id, actorDto)).thenReturn(mockUpdatedActor);

        // Act
        ResponseEntity<ActorDto> responseEntity = actorController.updateActor(id, actorDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockUpdatedActor, responseEntity.getBody());
    }

    @Test
    void testDeleteActor() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> responseEntity = actorController.deleteActor(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}

