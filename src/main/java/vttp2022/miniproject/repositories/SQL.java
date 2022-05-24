package vttp2022.miniproject.repositories;

public interface SQL {
    public static final String SQL_INSERT_INTO_SHOWS = "INSERT INTO shows (name, type, id, year, image_url, username) VALUES (?,?,?,?,?,?)";
    public static final String SQL_SELECT_ALL_SHOWS_BY_USERNAME = "SELECT * FROM shows WHERE username = ?";
    public static final String SQL_SELECT_SHOW_BY_USERNAME_AND_ID = "SELECT DISTINCT name, type, id, year, image_url, username FROM shows WHERE username = ? AND id = ? ORDER BY name, type, id, year, image_url, username";
    public static final String SQL_DELETE_SHOW_BY_USERNAME_AND_ID = "DELETE from shows where username = ? AND id = ?";

    public static final String SQL_INSERT_INTO_USERS = "INSERT INTO users (username, password) VALUES (?,sha1(?))";
    public static final String SQL_COUNT_USERS_BY_USERNAME = "select count(*) as user_count from users where username = ? and password = sha1(?)";
    public static final String SQL_SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    public static final String SQL_DELETE_USER_BY_USERNAME = "DELETE FROM users where username = ?";
}
