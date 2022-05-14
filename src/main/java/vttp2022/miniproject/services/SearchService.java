package vttp2022.miniproject.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.miniproject.models.Show;

@Service
public class SearchService {

    private static final String URL = "https://api.watchmode.com/v1/%s";
    private static final String AUTOCOMPLETE = "/autocomplete-search/";

    // WATCHMODE_API_KEY=rR2gQ4bW3NqFl1R4oiRHX7RGxxnZP0lVys9VnmzA
    @Value("${watchmode.api.key}")
    private String apiKey;

    // Autocomplete Search API /v1/autocomplete-search/
    // 'https://api.watchmode.com/v1/autocomplete-search/?apiKey=YOUR_API_KEY&search_value=Breaking%20bad&search_type=2'
    
    public List<Show> getTitlesByNameFromDb(String search) {

        List<Show> listOfResults = new LinkedList<>();

        String searchUrl = UriComponentsBuilder
            .fromUriString(URL.formatted(AUTOCOMPLETE))
            .queryParam("apiKey", apiKey)
            .queryParam("search_value", search.replaceAll(" ", "+"))
            .queryParam("search_type", 2)
            .toUriString();

        System.out.println(">>>>> Autocomplete Search HTTP Request: " + searchUrl);

        RequestEntity<Void> req = RequestEntity
                .get(searchUrl)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        System.out.println(">>>>> This is the req: " + req);
    
        RestTemplate template = new RestTemplate();
    
        ResponseEntity<String> resp = null;
            
        try {resp = template.exchange(req, String.class);
            System.out.println(">>>>> This is the resp: " + resp);
            } catch(Exception ex) {
                ex.printStackTrace();
            return listOfResults;
            }

        try (InputStream is = new ByteArrayInputStream(
                resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject data = reader.readObject();
            JsonArray jArray = data.getJsonArray("results");

            for (int i=0; i<jArray.size(); i++) {

                Show show = new Show();
                JsonObject arrObject = jArray.getJsonObject(i);
                String showName = arrObject.getString("name");
                String type = arrObject.getString("type");
                Integer id = arrObject.getInt("id");
                Integer year = arrObject.getInt("year");
                String imageUrl = arrObject.getString("image_url");

                show.setShowName(showName);
                show.setType(type);
                show.setId(id);
                show.setYear(year);
                show.setImageUrl(imageUrl);
                listOfResults.add(show);
            }
        } catch(IOException ex) {
            System.err.printf("+++ error: %s\n", ex.getMessage());
        }
        return listOfResults;
    }
    
    // {
    //     "results": [
    //       {
    //         "name": "Breaking Bad",
    //         "relevance": 445.23,
    //         "type": "tv_series",
    //         "id": 3173903,
    //         "year": 2008,
    //         "result_type": "title",
    //         "tmdb_id": 1396,
    //         "tmdb_type": "tv",
    //         "image_url": "https://cdn.watchmode.com/posters/03173903_poster_w185.jpg"
    //       }
    //     ]
    // }

}
