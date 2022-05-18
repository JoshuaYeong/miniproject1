package vttp2022.miniproject.repositories;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.miniproject.models.Show;

import static vttp2022.miniproject.repositories.SQL.*;

@Repository
public class ShowRepository {

    @Autowired
    private JdbcTemplate template;

    // public Integer insertSavedShow(final Show show) {
    //     KeyHolder keyHolder = new GeneratedKeyHolder();
    //     template.update(conn -> {
    //         PreparedStatement ps = conn.prepareStatement(SQL_INSERT_INTO_SHOWS,
    //              Statement.RETURN_GENERATED_KEYS);
    //         ps.setString(1, show.getShowName());
    //         ps.setString(2, show.getType());
    //         ps.setInt(3, show.getId());
    //         ps.setInt(4, show.getYear());
    //         ps.setString(5, show.getImageUrl());
    //         return ps;
    //     }, keyHolder);

    //     BigInteger bigInt = (BigInteger) keyHolder.getKey();
    //     return bigInt.intValue();
    // }

    // public int[] addShow(String username, Collection<Show> shows) {
    //     List<Object[]> params = shows.stream().map(v -> {
    //         Object[] row = new Object[7];
    //         row[0] = v.getShowId();
    //         row[1] = v.getShowName();
    //         row[2] = v.getType();
    //         row[3] = v.getId();
    //         row[4] = v.getYear();
    //         row[5] = v.getImageUrl();
    //         row[6] = username;
    //         return row;
    //     })
    //     .toList();

    //     return template.batchUpdate(SQL_INSERT_INTO_SHOWS, params);
    // }

    public boolean insertShow(String username, Show show) {
        int added = template.update(SQL_INSERT_INTO_SHOWS, show.getShowName(),
            show.getType(), show.getId(), show.getYear(), show.getImageUrl(), 
            username);

        return 1 == added;
    }

    public List<Show> selectAllShowsByUsername(String username) {
        SqlRowSet rs =template.queryForRowSet(SQL_SELECT_SHOWS_BY_USERNAME, username);

        List<Show> shows = new LinkedList<>();

        while(rs.next()) {
            Show sh = Show.create(rs);
            shows.add(sh);
        }

        return shows;
    }
}