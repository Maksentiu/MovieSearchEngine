package org.example.moviservice.cache;

import org.example.moviservice.dto.MovieDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MovieCache {
    private final Map<Long, MovieDto> movieCache = new HashMap<>();

    public MovieDto getMovieById(Long id) {
        return movieCache.get(id);
    }

    public void putMovieById(Long id, MovieDto movieDto) {
        movieCache.put(id, movieDto);
    }

    public void evictMovie(Long id) {
        movieCache.remove(id);
    }
}