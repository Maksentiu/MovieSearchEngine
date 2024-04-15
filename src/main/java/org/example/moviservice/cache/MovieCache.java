package org.example.moviservice.cache;

import org.example.moviservice.dto.MovieDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MovieCache {
    private final Map<Long, MovieDto> cache = new HashMap<>();

    public MovieDto getMovieById(Long id) {
        return cache.get(id);
    }

    public void putMovieById(Long id, MovieDto movieDto) {
        cache.put(id, movieDto);
    }

    public void removeMovie(Long id) {
        cache.remove(id);
    }

    public void updateMovieById(Long id, MovieDto movieDto) {
        if(getMovieById(id) != null){

            removeMovie(id);
            putMovieById(id, movieDto);
        }
    }

}