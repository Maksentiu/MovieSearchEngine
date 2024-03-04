package org.example.moviservice.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MovieDto {
    @JsonProperty
    private String title;
    @JsonProperty
    private String year;
    @JsonProperty
    private String imdbID;
    @JsonProperty
    private String type;
    @JsonProperty
    private String poster;

}
