package org.example.moviservice.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Search {
    @JsonProperty
    private String Title;
    @JsonProperty
    private String Year;
    @JsonProperty
    private String imdbID;
    @JsonProperty
    private String Type;
    @JsonProperty
    private String Poster;

    public String getTitle()
    {
        return this.Title;
    }
    public String getYear()
    {
        return this.Year;
    }
    public String getType()
    {
        return this.Type;
    }
    public String getImdbID()
    {
        return this.imdbID;
    }
    public String getPoster()
    {
        return this.Poster;
    }

}