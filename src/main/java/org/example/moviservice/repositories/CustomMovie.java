package org.example.moviservice.repositories;

import org.example.moviservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomMovie extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE m.year > :year")
    List<Movie> findMoviesReleasedAfterYear(int year);
}