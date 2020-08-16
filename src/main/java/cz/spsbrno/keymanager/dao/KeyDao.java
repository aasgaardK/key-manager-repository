package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.Key;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KeyDao {
    private JdbcTemplate jdbcTemplate;

    public KeyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Key> getAll(){
        String query = "SELECT * FROM door_key";
        return jdbcTemplate.query(query, new KeyRowMapper());
    }
}
