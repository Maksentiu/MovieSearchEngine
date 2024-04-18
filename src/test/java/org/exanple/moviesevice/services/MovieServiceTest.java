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
    void testGetAllMovies() {
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
    void testGetMoviesReleasedAfterYear() {
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
    void testGetMovieById() {
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
    void testCreateMovie() {
        // Создаем тестовые данные
        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Test Movie");
        movieDto.setYear(2022);
        movieDto.setDirector("Test Director");

        ActorDto actorDto = new ActorDto();
        actorDto.setId(1L);
        actorDto.setName("John Doe");

        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setContent("Test Comment");

        movieDto.setActors(Set.of(actorDto));
        movieDto.setComments(Set.of(commentDto));

        Actor actor = new Actor();
        actor.setId(1L);
        actor.setName("John Doe");

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setContent("Test Comment");

        // Устанавливаем поведение мокитированных репозиториев
        when(actorService.actorExist(actorDto)).thenReturn(actor);
        when(commentService.commentDtoToComment(commentDto)).thenReturn(comment);
        when(movieRepository.save(any(Movie.class))).thenReturn(new Movie());

        // Вызываем метод, который мы тестируем
        MovieDto createdMovieDto = movieService.createMovie(movieDto);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals("Test Movie", createdMovieDto.getTitle());
    }

    @Test
    void testUpdateMovie() {
        // Создаем тестовые данные
        MovieDto movieDto = new MovieDto();
        movieDto.setId(1L);
        movieDto.setTitle("Updated Movie");

        ActorDto actorDto = new ActorDto();
        actorDto.setId(1L);
        actorDto.setName("John Doe");

        movieDto.setActors(Set.of(actorDto));

        Actor existingActor = new Actor();
        existingActor.setId(1L);
        existingActor.setName("John Doe");

        // Устанавливаем поведение мокитированных репозиториев
        when(movieRepository.findById(1L)).thenReturn(Optional.of(new Movie()));
        when(actorService.actorExist(actorDto)).thenReturn(existingActor);
        when(movieRepository.save(any(Movie.class))).thenReturn(new Movie());

        // Вызываем метод, который мы тестируем
        MovieDto updatedMovieDto = movieService.updateMovie(1L, movieDto);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals("Updated Movie", updatedMovieDto.getTitle());
    }

    @Test
    void testDeleteMovie() {
        // Устанавливаем поведение мокитированного репозитория
        doNothing().when(movieRepository).deleteById(1L);

        // Вызываем метод, который мы тестируем
        movieService.deleteMovie(1L);

        // Проверяем, что метод deleteById был вызван один раз с нужным аргументом
        verify(movieRepository, times(1)).deleteById(1L);
    }

    @Test
    void testMoviesToMovieDtos() {
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
    void testMovieToMovieDto() {
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
