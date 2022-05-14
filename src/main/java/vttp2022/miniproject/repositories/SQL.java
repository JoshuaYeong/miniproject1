package vttp2022.miniproject.repositories;

public interface SQL {
    public static final String SQL_INSERT_INTO_SHOWS = "INSERT INTO shows (name, type, id, year, image_url, username)   VALUES (?,?,?,?,?,?)";
    public static final String SQL_SELECT_SHOWS_BY_USERNAME = "SELECT * FROM shows WHERE username = ?";

    public static final String SQL_INSERT_INTO_USERS = "INSERT INTO users (username, password) VALUES (?,sha1(?))";
    public static final String SQL_COUNT_USERS_BY_USERNAME = "select count(*) as user_count from users where username = ? and password = sha1(?)";
}
