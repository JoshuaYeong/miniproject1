package vttp2022.miniproject.models;

import jakarta.json.JsonObject;

public class Show {

    private Integer showId;
    private String showName;
    private String type;
    private Integer id;
    private Integer year;
    private String imageUrl;

    public Integer getShowId() {
        return showId;
    }
    public void setShowId(Integer showId) {
        this.showId = showId;
    }
    public String getShowName() {
        return showName;
    }
    public void setShowName(String showName) {
        this.showName = showName;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "showId: %d, showName: %s, type: %s, id: %d, year: %d, imageUrl: %s"
            .formatted(showId, showName, type, id, year, imageUrl);
    }

    public static Show create(JsonObject o) {
        final Show show = new Show();
        show.setShowName(o.getString("name"));
        show.setType(o.getString("type"));
        show.setId(o.getInt("id"));
        show.setYear(o.getInt("year"));
        show.setImageUrl(o.getString("image_url"));
        return show;
    }

}
