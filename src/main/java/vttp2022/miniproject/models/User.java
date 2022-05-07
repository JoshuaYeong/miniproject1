package vttp2022.miniproject.models;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class User {
    private Integer userId;
    private String username;
    private String email;
    private List<Show> shows = new LinkedList<>();

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<Show> getShows() {
        return shows;
    }
    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    private static User create(JsonObject readObject) {
        final User user = new User();
        user.setUsername(readObject.getString("username"));
        user.setEmail(readObject.getString("email"));
        List<Show> shows = readObject.getJsonArray("shows")
            .stream()
            .map(sh -> Show.create((JsonObject)sh))
            .toList();
        user.setShows(shows);
        return user;
    }

    public static User create(String jsonString) throws Exception {
        JsonReader reader = Json.createReader(
            new ByteArrayInputStream(jsonString.getBytes()));
        return create(reader.readObject());
    }

}
