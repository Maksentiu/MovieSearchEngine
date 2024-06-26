package org.example.moviservice.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.moviservice.dto.CommentDto;
import org.example.moviservice.services.CommentService;
import org.example.moviservice.services.RequestCounterService;
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
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final RequestCounterService requestCounterService;

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        List<CommentDto> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        CommentDto commentDto = commentService.getCommentById(id);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        CommentDto createdComment = commentService.createComment(commentDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        CommentDto updatedComment = commentService.updateComment(id, commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}