package org.example.moviservice.controllers;

import org.example.moviservice.dto.MovieDto;
import org.example.moviservice.services.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MovieController {
    @GetMapping("/movies")//поменять название

    public MovieDto[] movies(@RequestParam String query) throws IOException {
        MovieService omdbApi = new MovieService();
        return omdbApi.jsonToPojo(query);
    }
}
