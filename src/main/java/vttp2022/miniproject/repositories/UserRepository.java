package vttp2022.miniproject.repositories;

import static vttp2022.miniproject.repositories.SQL.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    
    @Autowired
    private JdbcTemplate template;

    public Integer countUsersByUsername(String username, String password) {
        
        SqlRowSet rs = template.queryForRowSet(SQL_COUNT_USERS_BY_USERNAME, username, password);
        if (!rs.next())
            return 0;
        return rs.getInt("user_count");
    }

    public boolean insertNewUser(String username, String password) {
        
        int added = template.update(SQL_INSERT_INTO_USERS, username, password);
        return 1 == added;
    }

    public boolean selectUserByUsername(String username) {
        
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_USER_BY_USERNAME, username);
        if (!rs.next())
            return false;
        return true;
    }

    public boolean deleteUser(String username) {

        int added = template.update(SQL_DELETE_USER_BY_USERNAME, username);
        return 1 == added;
    }
}
