package org.example.moviservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.moviservice.dto.MovieSearchListDto;
import org.example.moviservice.dto.MovieDto;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class MovieService {

    public MovieDto[] jsonToPojo(String query) throws IOException {
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

        ObjectMapper objMap = new ObjectMapper();
        MovieSearchListDto search = objMap.readValue(jsonContentBuilder.toString(), MovieSearchListDto.class);
        return search.getSearch();
    }
}
