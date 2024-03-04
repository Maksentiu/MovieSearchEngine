package org.example.moviservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieSearchListDto {
    @JsonProperty
    private MovieDto[] search;

    @JsonProperty
    private String totalResults;

    @JsonProperty
    private String Response;

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
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

    public void setSearch(MovieDto[] Search) {
        this.search = Search;
    }



}
