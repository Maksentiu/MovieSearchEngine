package org.example.moviservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieSearchListDto {
    @JsonProperty
    private MovieDto[] search;

    @JsonProperty
    private String totalResults;

    @JsonProperty
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public MovieDto[] getSearch() {
        return search;
    }

    public void setSearch(MovieDto[] search) {
        this.search = search;
    }

}
