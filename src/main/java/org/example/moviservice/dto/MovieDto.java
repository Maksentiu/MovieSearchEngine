package org.example.moviservice.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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

    public String getTitle()
    {
        return this.title;
    }
    public String getYear()
    {
        return this.year;
    }
    public String getType()
    {
        return this.type;
    }
    public String getImdbID()
    {
        return this.imdbID;
    }
    public String getPoster()
    {
        return this.poster;
    }

}