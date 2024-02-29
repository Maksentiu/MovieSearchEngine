package org.example.moviservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyclassFromJson {
    @JsonProperty
    private Search[] Search;

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

    public Search[] getSearch() {
        return Search;
    }

    public void setSearch(Search[] Search) {
        this.Search = Search;
    }



}
