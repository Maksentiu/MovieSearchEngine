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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    public Movie(long id, String title, int year, String director) {
        setId(id);
        setYear(year);
        setTitle(title);
        setDirector(director);
    }

    public void removeActor(Actor actor) {
        actors.remove(actor);
        actor.getMovies().remove(this);
    }
}