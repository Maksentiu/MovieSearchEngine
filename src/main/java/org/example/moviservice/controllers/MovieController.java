package org.example.moviservice.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.moviservice.dto.MovieDto;
import org.example.moviservice.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        log.info("Trying GET all movies");
        List<MovieDto> movies = movieService.getAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        log.info("Trying GET movie by id");
        MovieDto movieDto = movieService.getMovieById(id);
        return new ResponseEntity<>(movieDto, HttpStatus.OK);
    }

    @GetMapping("/after/{year}")
    public ResponseEntity<List<MovieDto>> getMoviesReleasedAfterYear(@PathVariable int year) {
        log.info("Trying GET movies released after year");
        List<MovieDto> movies = movieService.getMoviesReleasedAfterYear(year);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto) {
        log.info("Trying POST movie");
        MovieDto createdMovie = movieService.createMovie(movieDto);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long id, @RequestBody MovieDto movieDto) {
        log.info("Trying PUT movie");
        MovieDto updatedMovie = movieService.updateMovie(id, movieDto);
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        log.info("Trying DEL movie");
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}