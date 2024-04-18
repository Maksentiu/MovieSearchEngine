package org.exanple.moviesevice.cache;

import org.example.moviservice.cache.MovieCache;
import org.example.moviservice.dto.MovieDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class MovieCacheTest {

    private MovieCache movieCache;

    @BeforeEach
    void setUp() {
        movieCache = new MovieCache();
    }

    @Test
    void testGetMovieById_WhenCacheIsEmpty_ReturnsNull() {
        assertNull(movieCache.getMovieById(1L));
    }

    @Test
    void testPutMovieById() {
        // Arrange
        Long id = 1L;
        MovieDto movieDto = new MovieDto();
        movieDto.setId(id);
        movieDto.setTitle("Test Movie");

        // Act
        movieCache.putMovieById(id, movieDto);
        MovieDto retrievedMovie = movieCache.getMovieById(id);

        // Assert
        assertNotNull(retrievedMovie);
        assertEquals(id, retrievedMovie.getId());
        assertEquals("Test Movie", retrievedMovie.getTitle());
    }

    @Test
    void testRemoveMovie() {
        // Arrange
        Long id = 1L;
        MovieDto movieDto = new MovieDto();
        movieDto.setId(id);
        movieDto.setTitle("Test Movie");

        movieCache.putMovieById(id, movieDto);

        // Act
        movieCache.removeMovie(id);
        MovieDto retrievedMovie = movieCache.getMovieById(id);

        // Assert
        assertNull(retrievedMovie);
    }

    @Test
    void testUpdateMovieById_WhenMovieExists() {
        // Arrange
        Long id = 1L;
        MovieDto initialMovieDto = new MovieDto();
        initialMovieDto.setId(id);
        initialMovieDto.setTitle("Initial Movie");

        MovieDto updatedMovieDto = new MovieDto();
        updatedMovieDto.setId(id);
        updatedMovieDto.setTitle("Updated Movie");

        movieCache.putMovieById(id, initialMovieDto);

        // Act
        movieCache.updateMovieById(id, updatedMovieDto);
        MovieDto retrievedMovie = movieCache.getMovieById(id);

        // Assert
        assertNotNull(retrievedMovie);
        assertEquals(id, retrievedMovie.getId());
        assertEquals("Updated Movie", retrievedMovie.getTitle());
    }

    @Test
    void testUpdateMovieById_WhenMovieDoesNotExist() {
        // Arrange
        Long id = 1L;
        MovieDto updatedMovieDto = new MovieDto();
        updatedMovieDto.setId(id);
        updatedMovieDto.setTitle("Updated Movie");

        // Act
        movieCache.updateMovieById(id, updatedMovieDto);
        MovieDto retrievedMovie = movieCache.getMovieById(id);

        // Assert
        assertNull(retrievedMovie);
    }

    @Test
    void testEvictOldestEntry() {
        // Arrange
        Map<Long, MovieDto> cache = new HashMap<>();
        for (long i = 1; i <= 100; i++) {
            MovieDto movieDto = new MovieDto();
            movieDto.setId(i);
            movieDto.setTitle("Movie " + i);
            cache.put(i, movieDto);
        }

        // Act
        movieCache.evictOldestEntry();

        // Assert
        assertNull(movieCache.getMovieById(1L));
    }
}

