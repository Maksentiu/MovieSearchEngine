package org.example.moviservice.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ActorDto {
    private Long id;
    private String name;
    private Set<MovieDto> movies = new HashSet<>();
}