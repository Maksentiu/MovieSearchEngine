package org.exanple.moviesevice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.moviservice.controllers.CommentController;
import org.example.moviservice.dto.CommentDto;
import org.example.moviservice.services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommentControllerTest {

    private final CommentController commentController;
    private final CommentService commentService;
    private final ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        commentService = mock(CommentService.class);
        commentController = new CommentController(commentService);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllComments() {
        // Arrange
        List<CommentDto> mockComments = List.of(new CommentDto(), new CommentDto());
        when(commentService.getAllComments()).thenReturn(mockComments);

        // Act
        ResponseEntity<List<CommentDto>> responseEntity = commentController.getAllComments();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockComments, responseEntity.getBody());
    }

    @Test
    void testGetCommentById() {
        // Arrange
        Long id = 1L;
        CommentDto mockComment = new CommentDto();
        when(commentService.getCommentById(id)).thenReturn(mockComment);

        // Act
        ResponseEntity<CommentDto> responseEntity = commentController.getCommentById(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockComment, responseEntity.getBody());
    }

    @Test
    void testCreateComment() throws Exception {
        // Arrange
        CommentDto commentDto = new CommentDto();
        commentDto.setContent("Test Comment");
        String requestBody = objectMapper.writeValueAsString(commentDto);

        CommentDto mockCreatedComment = new CommentDto();
        when(commentService.createComment(commentDto)).thenReturn(mockCreatedComment);

        // Act
        ResponseEntity<CommentDto> responseEntity = commentController.createComment(commentDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockCreatedComment, responseEntity.getBody());
    }

    @Test
    void testUpdateComment() throws Exception {
        // Arrange
        Long id = 1L;
        CommentDto commentDto = new CommentDto();
        commentDto.setContent("Updated Comment");
        String requestBody = objectMapper.writeValueAsString(commentDto);

        CommentDto mockUpdatedComment = new CommentDto();
        when(commentService.updateComment(id, commentDto)).thenReturn(mockUpdatedComment);

        // Act
        ResponseEntity<CommentDto> responseEntity = commentController.updateComment(id, commentDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockUpdatedComment, responseEntity.getBody());
    }

    @Test
    void testDeleteComment() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> responseEntity = commentController.deleteComment(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}

