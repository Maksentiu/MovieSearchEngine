package org.exanple.moviesevice.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.moviservice.cache.MovieCache;
import org.example.moviservice.dto.ActorDto;
import org.example.moviservice.dto.CommentDto;
import org.example.moviservice.dto.MovieDto;
import org.example.moviservice.model.Actor;
import org.example.moviservice.model.Comment;
import org.example.moviservice.model.Movie;
import org.example.moviservice.repositories.ActorRepository;
import org.example.moviservice.repositories.CommentRepository;
import org.example.moviservice.repositories.CustomMovie;
import org.example.moviservice.repositories.MovieRepository;
import org.example.moviservice.services.ActorService;
import org.example.moviservice.services.CommentService;
import org.example.moviservice.services.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CustomMovie customMovieRepository;

    @Mock
    private ActorService actorService;

    @Mock
    private CommentService commentService;

    @Mock
    private MovieCache movieCache;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllMovies() {
        // Создаем тестовые данные
        List<Movie> movies = List.of(
                new Movie(1L, "Movie 1", 2022, "Director 1"),
                new Movie(2L, "Movie 2", 2023, "Director 2")
        );
        when(movieRepository.findAll()).thenReturn(movies);

        // Вызываем метод, который мы тестируем
        List<MovieDto> movieDtos = movieService.getAllMovies();

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(2, movieDtos.size());
        assertEquals("Movie 1", movieDtos.get(0).getTitle());
        assertEquals(2022, movieDtos.get(0).getYear());
        assertEquals("Director 1", movieDtos.get(0).getDirector());
    }

    @Test
    public void testGetMoviesReleasedAfterYear() {
        // Создаем тестовые данные
        List<Movie> movies = List.of(
                new Movie(1L, "Movie 1", 2022, "Director 1"),
                new Movie(2L, "Movie 2", 2023, "Director 2")
        );
        when(customMovieRepository.findMoviesReleasedAfterYear(2021)).thenReturn(movies);

        // Вызываем метод, который мы тестируем
        List<MovieDto> movieDtos = movieService.getMoviesReleasedAfterYear(2021);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(2, movieDtos.size());
        assertEquals("Movie 1", movieDtos.get(0).getTitle());
        assertEquals(2022, movieDtos.get(0).getYear());
        assertEquals("Director 1", movieDtos.get(0).getDirector());
    }

    @Test
    public void testGetMovieById() {
        // Создаем тестовые данные
        Movie movie = new Movie(1L, "Test Movie", 2022, "Test Director");
        MovieDto movieDto = new MovieDto();
        movieDto.setId(1L);
        movieDto.setTitle("Test Movie");
        movieDto.setYear(2022);
        movieDto.setDirector("Test Director");

        // Устанавливаем поведение мокитированных репозиториев
        when(movieCache.getMovieById(1L)).thenReturn(null);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        // Вызываем метод, который мы тестируем
        MovieDto resultMovieDto = movieService.getMovieById(1L);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals("Test Movie", resultMovieDto.getTitle());
        assertEquals(2022, resultMovieDto.getYear());
        assertEquals("Test Director", resultMovieDto.getDirector());
    }


    @Test
    public void testDeleteMovie() {
        // Устанавливаем поведение мокитированного репозитория
        doNothing().when(movieRepository).deleteById(1L);

        // Вызываем метод, который мы тестируем
        movieService.deleteMovie(1L);

        // Проверяем, что метод deleteById был вызван один раз с нужным аргументом
        verify(movieRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testMoviesToMovieDtos() {
        // Создаем тестовые данные
        List<Movie> movies = List.of(
                new Movie(1L, "Movie 1", 2022, "Director 1"),
                new Movie(2L, "Movie 2", 2023, "Director 2")
        );

        // Вызываем метод, который мы тестируем
        List<MovieDto> movieDtos = movieService.moviesToMovieDtos(movies);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(2, movieDtos.size());
        assertEquals("Movie 1", movieDtos.get(0).getTitle());
        assertEquals(2022, movieDtos.get(0).getYear());
        assertEquals("Director 1", movieDtos.get(0).getDirector());
    }

    @Test
    public void testMovieToMovieDto() {
        // Создаем тестовые данные
        Movie movie = new Movie(1L, "Test Movie", 2022, "Test Director");

        // Вызываем метод, который мы тестируем
        MovieDto movieDto = movieService.movieToMovieDto(movie);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals("Test Movie", movieDto.getTitle());
        assertEquals(2022, movieDto.getYear());
        assertEquals("Test Director", movieDto.getDirector());
    }
}
