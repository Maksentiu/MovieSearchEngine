package org.example.moviservice.services;

import lombok.AllArgsConstructor;
import org.example.moviservice.cache.MovieCache;
import org.example.moviservice.dto.ActorDto;
import org.example.moviservice.dto.CommentDto;
import org.example.moviservice.dto.MovieDto;
import org.example.moviservice.model.Actor;
import org.example.moviservice.model.Comment;
import org.example.moviservice.model.Movie;
import org.example.moviservice.repositories.ActorRepository;
import org.example.moviservice.repositories.CommentRepository;
import org.example.moviservice.repositories.CustomQueryRepository;
import org.example.moviservice.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final CommentRepository commentRepository;
    private final CustomQueryRepository customQueryRepository;
    private final ActorService actorService;
    private final CommentService commentService;
    private final MovieCache movieCache;

    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(this::movieToMovieDto)
                .toList();
    }

    public List<MovieDto> getMoviesReleasedAfterYear(int year) {
        return moviesToMovieDtos(customQueryRepository.findMoviesReleasedAfterYear(year));
    }

    public MovieDto getMovieById(Long id) {
        MovieDto movieDto = movieCache.getMovieById(id);

        if (movieDto == null) {
            Optional<Movie> movieOptional = movieRepository.findById(id);
            if (movieOptional.isPresent()) {
                movieDto = movieToMovieDto(movieOptional.get());
                movieCache.putMovieById(id, movieDto);
            }
        }

        return movieDto;
    }



    public MovieDto createMovie(MovieDto movieDto) {
        Movie movie = existMovie(movieDto);

        Set<Actor> actors = new HashSet<>();
        for (ActorDto actorDto : movieDto.getActors()) {
            Actor existingActor = actorService.actorExist(actorDto);
            actors.add(existingActor);
        }
        movie.setActors(actors);

        movie = movieRepository.save(movie);

        Set<Comment> comments = new HashSet<>();
        if (movieDto.getComments() != null && !movieDto.getComments().isEmpty()) {
            for (CommentDto commentDto : movieDto.getComments()) {
                Comment comment = new Comment();
                comment.setContent(commentDto.getContent());
                comment.setMovie(movie);
                comments.add(comment);
            }
        }
        movie.setComments(comments);

        return movieToMovieDto(movie);
    }

    public MovieDto updateMovie(Long id, MovieDto movieDto) {
        Optional<Movie> optionalExistingMovie = movieRepository.findById(id);
        Movie movie = optionalExistingMovie.get();
        movie.setTitle(movieDto.getTitle());
        movie.setYear(movieDto.getYear());
        movie.setDirector(movie.getDirector());

        Set<Actor> actors = new HashSet<>();
        for (ActorDto actorDto : movieDto.getActors()) {
            Actor existingActor = actorService.actorExist(actorDto); // Проверяем существует ли актер
            actors.add(existingActor);
        }

        movie.setActors(actors);

        movie = movieRepository.save(movie);

        Set<CommentDto> newComments = movieDto.getComments();
        if (newComments != null && !newComments.isEmpty()) {
            for (CommentDto commentDto : newComments) {
                Comment comment = new Comment();
                comment.setContent(commentDto.getContent());
                comment.setMovie(movie);
                commentRepository.save(comment);

            }
        }

        return movieToMovieDto(movie);
    }

    public void deleteMovie(Long id) {

        movieRepository.findById(id).ifPresent(movie -> {
            movie.getActors().forEach(actor -> actor.getMovies().remove(movie));
            movie.setActors(new HashSet<>());
            movieRepository.save(movie);
        });

        movieRepository.deleteById(id);
    }

    public List<MovieDto> moviesToMovieDtos(List<Movie> movies) {
        return movies.stream()
                .map(this::movieToMovieDto)
                .toList();
    }

    public MovieDto movieToMovieDto(Movie movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setYear(movie.getYear());
        movieDto.setDirector(movie.getDirector());

        movieDto.setActors(movie.getActors().stream()
                .map(actorService::actorToActorDto)
                .collect(Collectors.toSet()));

        movieDto.setComments(movie.getComments().stream()
                .map(commentService::commentToCommentDto)
                .collect(Collectors.toSet()));

        return movieDto;
    }

    public Movie existMovie(MovieDto movieDto){
        long flag = 0;
        for(Movie movie : movieRepository.findAll()){
            if(movie.getTitle().equals(movieDto.getTitle()))
                flag++;
        }
        if (flag == 0) {
            return movieDtoToMovieWithSave(movieDto);
        }
        return null;
    }

    private Movie movieDtoToMovieWithSave(MovieDto movieDto) {
        Movie movie = new Movie();

        movie.setId(movieDto.getId());
        movie.setTitle(movieDto.getTitle());
        movie.setYear(movieDto.getYear());
        movie.setDirector(movieDto.getDirector());

        Set<Comment> commentsToSave = new HashSet<>();
        for (CommentDto commentDto : movieDto.getComments()) {
            Comment comment = new Comment();
            comment.setContent(commentDto.getContent());
            comment.setMovie(movie);
            commentsToSave.add(comment);
        }

        for (Comment comment : commentsToSave) {
            commentRepository.save(comment);
        }

        Set<Actor> actorsToSave = new HashSet<>();
        for (ActorDto actorDto : movieDto.getActors()) {
            Actor actor = actorService.actorExist(actorDto);
            actorsToSave.add(actor);
        }

        for (Actor actor : actorsToSave) {
            actorRepository.save(actor);
        }

        Set<Actor> actors = new HashSet<>();
        for (Actor actor : actorsToSave) {
            actors.add(actor);
        }
        movie.setActors(actors);

        return movie;
    }
}