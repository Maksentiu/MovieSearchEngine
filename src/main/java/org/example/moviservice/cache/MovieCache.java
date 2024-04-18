package org.example.moviservice.cache;

import org.example.moviservice.dto.MovieDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MovieCache {
    private final Map<Long, MovieDto> cache = new HashMap<>();

    public MovieDto getMovieById(Long id) {

        if (cache.size() == 100) {
            evictOldestEntry();
        }
        return cache.get(id);
    }

    public void evictOldestEntry() {
        Long oldestKey = null;
        for (Long key : cache.keySet()) {
            if (oldestKey == null || key < oldestKey) {
                oldestKey = key;
            }
        }
        if (oldestKey != null) {
            cache.remove(oldestKey);
        }
    }

    public void putMovieById(Long id, MovieDto movieDto) {
        cache.put(id, movieDto);
    }

    public void removeMovie(Long id) {
        cache.remove(id);
    }

    public void updateMovieById(Long id, MovieDto movieDto) {
        if (getMovieById(id) != null) {

            removeMovie(id);
            putMovieById(id, movieDto);
        }
    }

}