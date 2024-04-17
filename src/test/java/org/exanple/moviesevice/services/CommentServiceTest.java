package org.exanple.moviesevice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.example.moviservice.dto.CommentDto;
import org.example.moviservice.model.Comment;
import org.example.moviservice.repositories.CommentRepository;
import org.example.moviservice.services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class CommentServiceTest {

    private CommentService commentService;
    private CommentRepository commentRepository;

    @BeforeEach
    public void setUp() {
        commentRepository = mock(CommentRepository.class);
        commentService = new CommentService(commentRepository);
    }

    @Test
    public void testGetAllComments() {
        // Создаем тестовый комментарий и список комментариев
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setContent("Test Comment");
        List<Comment> comments = List.of(comment);

        // Устанавливаем поведение мок репозитория
        when(commentRepository.findAll()).thenReturn(comments);

        // Вызываем метод, который мы тестируем
        List<CommentDto> commentDtos = commentService.getAllComments();

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(1, commentDtos.size());
        assertEquals(1L, commentDtos.get(0).getId());
        assertEquals("Test Comment", commentDtos.get(0).getContent());
    }

    @Test
    public void testGetCommentById() {
        // Создаем тестовый комментарий
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setContent("Test Comment");

        // Устанавливаем поведение мок репозитория
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        // Вызываем метод, который мы тестируем
        CommentDto commentDto = commentService.getCommentById(1L);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(1L, commentDto.getId());
        assertEquals("Test Comment", commentDto.getContent());
    }

    @Test
    public void testCreateComment() {
        // Создаем тестовый DTO комментария
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setContent("Test Comment");

        // Создаем мок комментария и устанавливаем поведение мок репозитория
        Comment comment = new Comment();
        when(commentRepository.save(comment)).thenReturn(comment);

        // Вызываем метод, который мы тестируем
        CommentDto createdComment = commentService.createComment(commentDto);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(1L, createdComment.getId());
        assertEquals("Test Comment", createdComment.getContent());
    }

    @Test
    public void testDeleteComment() {
        // Проверяем, что метод вызывает deleteById с правильным id
        commentService.deleteComment(1L);
        // Проверяем, что метод deleteById был вызван один раз для комментария с id 1L
        verify(commentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateComment() {
        // Создаем тестовый комментарий и его DTO
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setContent("Updated Comment");

        Comment existingComment = new Comment();
        existingComment.setId(1L);
        existingComment.setContent("Old Comment");

        // Устанавливаем поведение мок репозитория
        when(commentRepository.findById(1L)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(existingComment)).thenReturn(existingComment);

        // Вызываем метод, который мы тестируем
        CommentDto updatedComment = commentService.updateComment(1L, commentDto);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(1L, updatedComment.getId());
        assertEquals("Updated Comment", updatedComment.getContent());
    }

    @Test
    public void testCommentToCommentDto() {
        // Создаем тестовый комментарий
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setContent("Test Comment");

        // Вызываем метод, который мы тестируем
        CommentDto commentDto = commentService.commentToCommentDto(comment);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(1L, commentDto.getId());
        assertEquals("Test Comment", commentDto.getContent());
    }

    @Test
    public void testCommentDtoToComment() {
        // Создаем тестовый DTO комментария
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setContent("Test Comment");

        // Вызываем метод, который мы тестируем
        Comment comment = commentService.commentDtoToComment(commentDto);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(1L, comment.getId());
        assertEquals("Test Comment", comment.getContent());
    }

    @Test
    public void testUpdateCommentFromDto() {
        // Создаем тестовый комментарий и его DTO
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setContent("Updated Comment");

        Comment existingComment = new Comment();
        existingComment.setId(1L);
        existingComment.setContent("Old Comment");

        // Вызываем метод, который мы тестируем
        commentService.updateCommentFromDto(commentDto, existingComment);

        // Проверяем, что комментарий был обновлен правильно
        assertEquals("Updated Comment", existingComment.getContent());
    }

}
