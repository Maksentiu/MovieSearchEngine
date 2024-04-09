package org.example.moviservice.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int year;
    private String director;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    public void addActor(Actor actor) {
        actors.add(actor);
        actor.getMovies().add(this); // Добавляем фильм в список фильмов актера
    }

    public void removeActor(Actor actor) {
        actors.remove(actor);
        actor.getMovies().remove(this); // Удаляем фильм из списка фильмов актера
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setMovie(this); // Устанавливаем связь с фильмом в комментарии
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setMovie(null); // Убираем связь с фильмом у комментария
    }
}
