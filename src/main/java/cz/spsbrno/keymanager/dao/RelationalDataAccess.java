package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RelationalDataAccess {
    private final JdbcTemplate jdbcTemplate;

    public RelationalDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User createUser(User user)
    {
        jdbcTemplate.execute("insert into user(name, surname) values ("+user.getName()+", "+user.getSurname()+")");
        jdbcTemplate.query("select * from user", (rs, rowNum)->{
            new User(rs.getInt("id"));
        });
    }
}
