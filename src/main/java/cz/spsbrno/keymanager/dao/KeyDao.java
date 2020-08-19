package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Key createKey(Key key) {
        DataSource dataSource;
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("door_key")
                .usingGeneratedKeyColumns("Door_Key_ID");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("Code", key.getCode());

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

        return getKeyById(id.intValue());
    }

    public Key getKeyById(int id) {
        String query = "SELECT * FROM `door_key` WHERE Door_Key_ID = " + id;
        return jdbcTemplate.queryForObject(query, new KeyRowMapper());
    }

}
