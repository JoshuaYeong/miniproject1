package vttp2022.miniproject.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.miniproject.models.Show;

import static vttp2022.miniproject.repositories.SQL.*;

@Repository
public class ShowRepository {

    @Autowired
    private JdbcTemplate template;

    public boolean insertShow(String username, Show show) {
        int added = template.update(SQL_INSERT_INTO_SHOWS, show.getShowName(),
            show.getType(), show.getId(), show.getYear(), show.getImageUrl(), 
            username);

        return 1 == added;
    }

    public List<Show> selectAllShowsByUsername(String username) {
        SqlRowSet rs =template.queryForRowSet(SQL_SELECT_ALL_SHOWS_BY_USERNAME, username);

        List<Show> shows = new LinkedList<>();

        while(rs.next()) {
            Show sh = Show.create(rs);
            shows.add(sh);
        }

        return shows;
    }

    public boolean selectShowByUsername(String username) {
        
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_SHOW_BY_USERNAME, username);
        if (!rs.next())
            return false;
        return true;
    }

    public boolean deleteShow(String username, Integer titleId) {
        int added = template.update(SQL_DELETE_SHOW_BY_USERNAME_AND_ID, username, titleId);

        return 1 == added;
    }
}