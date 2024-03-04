package org.example.moviservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.moviservice.model.MyclassFromJson;
import org.example.moviservice.model.Search;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class MoviesController {

    public String searchPage(String query) throws IOException {
        String url = "https://www.omdbapi.com/?s=" + query + "&page=1&apikey=19b0b951";
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder jsonContentBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonContentBuilder.append(line);
        }
        reader.close();
        
        return jsonContentBuilder.toString();
    }

    public Search[] jsonToPojo(String jsonContent) throws IOException{
        ObjectMapper objMap = new ObjectMapper();
        MyclassFromJson search = objMap.readValue(jsonContent, MyclassFromJson.class);
        return search.getSearch();
    }

    @GetMapping("/movies")//поменять название
    public Search[] movies(@RequestParam String query) throws IOException {
        return jsonToPojo(searchPage(query));
    }
}
