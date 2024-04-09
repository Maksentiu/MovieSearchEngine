package org.example.moviservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.moviservice.model.Actor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class MovieDto {
    private Long id;
    private String title;
    private int year;
    private String director;
    private Set<ActorDto> actors = new HashSet<>();
    private Set<CommentDto> comments = new HashSet<>();
}
