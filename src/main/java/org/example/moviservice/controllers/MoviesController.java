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
        String jsonContent = "";
        String line;
        while ((line = reader.readLine()) != null) {
            jsonContent += line;
        }
        reader.close();
        return jsonContent;
    }

    public Search[] jsonToPojo(String jsonContent) throws IOException{
        ObjectMapper objMap = new ObjectMapper();
        MyclassFromJson Search = objMap.readValue(jsonContent, MyclassFromJson.class);
        return Search.getSearch();
    }

    @GetMapping("/movies")//поменять название
    public Search[] movies() throws IOException {
        String query = "Thor: Ragnarok";
        System.out.println(searchPage(query));
        return jsonToPojo(searchPage(query));
    }
}
