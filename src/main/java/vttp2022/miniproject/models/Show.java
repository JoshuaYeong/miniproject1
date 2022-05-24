package vttp2022.miniproject.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Show {

    private Integer sid;
    private String showName;
    private String type;
    private Integer id;
    private Integer year;
    private String imageUrl;
    private String username;

    public Integer getSid() {
        return sid;
    }
    public void setSid(Integer sid) {
        this.sid = sid;
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
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "sid: %d, showName: %s, type: %s, id: %d, year: %d, imageUrl: %s"
            .formatted(sid, showName, type, id, year, imageUrl);
    }

    public static Show create(SqlRowSet rs) {
        Show show = new Show();
        show.setShowName(rs.getString("name"));
        show.setType(rs.getString("type"));
        show.setId(rs.getInt("id"));
        show.setYear(rs.getInt("year"));
        show.setImageUrl(rs.getString("image_url"));
        return show;
    }

}
