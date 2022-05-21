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
    private static final String DETAILS = "/title/%d/details/";

    @Value("${watchmode.api.key}")
    private String apiKey;
    
    public List<Show> getTitlesByNameFromDb(String search) {

        List<Show> listOfResults = new LinkedList<>();

        String searchUrl = UriComponentsBuilder
            .fromUriString(URL.formatted(AUTOCOMPLETE))
            .queryParam("apiKey", apiKey)
            .queryParam("search_value", search.replaceAll(" ", "+"))
            .queryParam("search_type", 2)
            .toUriString();

        RequestEntity<Void> req = RequestEntity
                .get(searchUrl)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        // System.out.println(">>>>> This is the req: " + req);
    
        RestTemplate template = new RestTemplate();
    
        ResponseEntity<String> resp = null;
            
        try {resp = template.exchange(req, String.class);
            // System.out.println(">>>>> This is the resp: " + resp);
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
                Integer id = (Integer) arrObject.getInt("id");
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

    public Show getDetailsByIdFromDb(Integer titleId) {

        Show showDetails = new Show();

        String searchUrl = UriComponentsBuilder
        .fromUriString(URL.formatted(DETAILS.formatted(titleId)))
        .queryParam("apiKey", apiKey)
        .toUriString();

        RequestEntity<Void> req = RequestEntity
            .get(searchUrl)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        // System.out.println(">>>>> This is the req: " + req);
    
        RestTemplate template = new RestTemplate();
    
        ResponseEntity<String> resp = null;
            
        try {resp = template.exchange(req, String.class);
            // System.out.println(">>>>> This is the resp: " + resp);
            } catch(Exception ex) {
                ex.printStackTrace();
            return showDetails;
            }
        
        try (InputStream is = new ByteArrayInputStream(
            resp.getBody().getBytes())) {
                JsonReader reader = Json.createReader(is);
                JsonObject data = reader.readObject();

                String title = data.getString("title");
                String type = data.getString("type");
                Integer id = data.getInt("id");
                Integer year = data.getInt("year");
                String poster = data.getString("poster");

                showDetails.setShowName(title);
                showDetails.setType(type);
                showDetails.setId(id);
                showDetails.setYear(year);
                showDetails.setImageUrl(poster);

        } catch(IOException ex) {
            System.err.printf("+++ error: %s\n", ex.getMessage());
        }
        return showDetails;
    }
}
