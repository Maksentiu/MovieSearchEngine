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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MovieService {

    public static void replaceAll(StringBuilder builder, String from, String to) {
        int index = builder.indexOf(from);
        while (index != -1) {
            builder.replace(index, index + from.length(), to);
            index += to.length(); // Переходим к концу замененной строки
            index = builder.indexOf(from, index);
        }
    }
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

        replaceAll(jsonContentBuilder, "Title", "title");
        replaceAll(jsonContentBuilder, "Year", "year");
        replaceAll(jsonContentBuilder, "Poster", "poster");
        replaceAll(jsonContentBuilder, "Type", "type");
        replaceAll(jsonContentBuilder, "Search", "search");

        ObjectMapper objMap = new ObjectMapper();
        MovieSearchListDto search = objMap.readValue(jsonContentBuilder.toString(), MovieSearchListDto.class);
        return search.getSearch();
    }
}
