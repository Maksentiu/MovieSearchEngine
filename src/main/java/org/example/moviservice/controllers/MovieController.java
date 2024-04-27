package org.example.moviservice.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.moviservice.dto.MovieDto;
import org.example.moviservice.services.MovieService;
import org.example.moviservice.services.RequestCounterService;
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
    private final RequestCounterService requestCounterService;

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        List<MovieDto> movies = movieService.getAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        MovieDto movieDto = movieService.getMovieById(id);
        return new ResponseEntity<>(movieDto, HttpStatus.OK);
    }

    @GetMapping("/after/{year}")
    public ResponseEntity<List<MovieDto>> getMoviesReleasedAfterYear(@PathVariable int year) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        List<MovieDto> movies = movieService.getMoviesReleasedAfterYear(year);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        MovieDto createdMovie = movieService.createMovie(movieDto);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @PostMapping("/bulk-create")
    public ResponseEntity<String> createMovieBulk(@RequestBody List<MovieDto> movieDtoList) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        movieService.createMovieBulk(movieDtoList);
        return ResponseEntity.status(HttpStatus.CREATED).body("Bulk movie creation successful");
    }

    @PostMapping("/bulk-update")
    public ResponseEntity<String> updateCipherBulk(@RequestBody List<MovieDto> movieDtoList) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        movieService.updateCipherBulk(movieDtoList);
        return ResponseEntity.status(HttpStatus.OK).body("Bulk movie update successful");
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long id, @RequestBody MovieDto movieDto) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        MovieDto updatedMovie = movieService.updateMovie(id, movieDto);
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        log.info(String.valueOf(requestCounterService.incrementAndGet()));
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}