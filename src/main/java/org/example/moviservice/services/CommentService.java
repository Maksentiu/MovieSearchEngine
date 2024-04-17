package org.example.moviservice.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.moviservice.dto.CommentDto;
import org.example.moviservice.model.Comment;
import org.example.moviservice.repositories.CommentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public List<CommentDto> getAllComments() {
        log.info("Trying GET all comments");
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(this::commentToCommentDto)
                .toList();
    }

    public CommentDto getCommentById(Long id) {
        log.info("Trying GET comment by id");
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
        return commentToCommentDto(comment);
    }

    public CommentDto createComment(CommentDto commentDto) {
        log.info("Trying create comment");
        Comment comment = commentDtoToComment(commentDto);
        comment = commentRepository.save(comment);
        return commentToCommentDto(comment);
    }

    public CommentDto updateComment(Long id, CommentDto commentDto) {
        log.info("Trying update comment by id: {}", id);
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment " + id + " not found"));

        updateCommentFromDto(commentDto, existingComment);
        existingComment.setId(id);
        existingComment.setContent(commentDto.getContent());
        log.info("Trying save comment by id: {}", id);
        existingComment = commentRepository.save(existingComment);
        return commentToCommentDto(existingComment);
    }

    public void deleteComment(Long id) {
        log.info("Trying DELETE comment by id: {}", id);
        commentRepository.deleteById(id);
    }

    public CommentDto commentToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        return commentDto;
    }

    public Comment commentDtoToComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setContent(commentDto.getContent());
        return comment;
    }

    public void updateCommentFromDto(CommentDto commentDto, Comment comment) {
        comment.setContent(commentDto.getContent());
    }
}