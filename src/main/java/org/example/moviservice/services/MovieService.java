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
    private final ActorService actorService;
    private final MovieCache movieCache;

    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(this::movieToMovieDto)
                .toList();
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
        Movie movie = movieDtoToMovie(movieDto);

        // Обработка актеров
        Set<Actor> actors = new HashSet<>();
        for (ActorDto actorDto : movieDto.getActors()) {
            Actor existingActor = actorService.actorExist(actorDto); // Проверяем существует ли актер
            actors.add(existingActor);
        }
        movie.setActors(actors);

        movie = movieRepository.save(movie);

        // Добавляем комментарии к фильму
        Set<CommentDto> comments = movieDto.getComments();
        if (comments != null && !comments.isEmpty()) {
            for (CommentDto commentDto : comments) {
                Comment comment = new Comment();
                comment.setContent(commentDto.getContent());
                comment.setMovie(movie);
            }
        }

        return movieToMovieDto(movie);
    }

    public MovieDto updateMovie(Long id, MovieDto movieDto) {
        Optional<Movie> optionalExistingMovie = movieRepository.findById(id);
        Movie movie = optionalExistingMovie.get();

        // Обработка актеров
        Set<Actor> actors = new HashSet<>();
        for (ActorDto actorDto : movieDto.getActors()) {
            Actor existingActor = actorService.actorExist(actorDto); // Проверяем существует ли актер
            actors.add(existingActor);
        }

        movie.setActors(actors);

        movie = movieRepository.save(movie);

        // Добавляем новые комментарии к фильму
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
        // Удаление связанных записей в таблице "movie_actor"
        movieRepository.findById(id).ifPresent(movie -> {
            movie.getActors().forEach(actor -> actor.getMovies().remove(movie));
            movie.setActors(new HashSet<>());
            movieRepository.save(movie);
        });

        // Теперь можно удалить сам фильм
        movieRepository.deleteById(id);
    }

    public MovieDto movieToMovieDto(Movie movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setYear(movie.getYear());
        movieDto.setDirector(movie.getDirector());

        Set<CommentDto> commentDtos = movie.getComments().stream()
                .map(comment -> {
                    CommentDto commentDto = new CommentDto();
                    commentDto.setId(comment.getId());
                    commentDto.setContent(comment.getContent());
                    return commentDto;
                })
                .collect(Collectors.toSet());

        movieDto.setComments(commentDtos);

        Set<ActorDto> actorDtos = movie.getActors().stream()
                .map(actor -> {
                    ActorDto actorDto = new ActorDto();
                    actorDto.setId(actor.getId());
                    actorDto.setName(actor.getName());
                    return actorDto;
                })
                .collect(Collectors.toSet());

        movieDto.setActors(actorDtos);
        return movieDto;
    }

    private Movie movieDtoToMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setId(movieDto.getId());
        movie.setTitle(movieDto.getTitle());
        movie.setYear(movieDto.getYear());
        movie.setDirector(movieDto.getDirector());

        Set<Comment> commentsToSave = new HashSet<>();
        for (CommentDto commentDto : movieDto.getComments()) {
            Comment comment = new Comment();
            comment.setContent(commentDto.getContent());
            comment.setMovie(movie); // Устанавливаем связь с фильмом
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

    public void updateMovieFromDto(MovieDto movieDto, Movie movie) {
        movie.setTitle(movieDto.getTitle());
        movie.setYear(movieDto.getYear());
        movie.setDirector(movieDto.getDirector());

        Set<Actor> updatedActors = new HashSet<>();

        for (ActorDto actorDto : movieDto.getActors()) {
            boolean actorExists = false;
            for (Actor existingActor : movie.getActors()) {
                if (existingActor.getName().equals(actorDto.getName())) {
                    existingActor.setName(actorDto.getName());
                    updatedActors.add(existingActor);
                    actorExists = true;
                    break;
                }
            }
            if (!actorExists) {
                Actor newActor = new Actor();
                newActor.setName(actorDto.getName());
                // Сохраняем нового актера и получаем его с присвоенным ID
                newActor = actorRepository.save(newActor);
                updatedActors.add(newActor);
            }
        }

        movie.setActors(updatedActors);
    }
}