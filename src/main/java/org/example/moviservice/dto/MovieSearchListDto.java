package org.example.moviservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MovieSearchListDto {
    @JsonProperty
    private MovieDto[] search;

    @JsonProperty
    private String totalResults;

    @JsonProperty
    private String response;

}
