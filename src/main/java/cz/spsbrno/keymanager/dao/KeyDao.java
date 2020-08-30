package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.data.relational.core.query.Update;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.security.KeyStore;
import java.util.ArrayList;
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
//    public Key deleteKey(Key key) {
//        DataSource dataSource;
//        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("door_key")
//                .usingGeneratedKeyColumns("Door_Key_ID");
//        Map<String, Object> parameters = new HashMap<String, Object>();
//        parameters.put("Code", key.getCode());
//
//        Number id = simpleJdbcInsert.execute(parameters);
//
//        return deleteKeyById(id.intValue());
//    }

    public Key getKeyById(int id) {
        String query = "SELECT * FROM `door_key` WHERE Door_Key_ID = " + id;
        return jdbcTemplate.queryForObject(query, new KeyRowMapper());
    }

//    public Key deleteKeyById(int id){
//        String query = "DELETE  FROM `door_key` WHERE Door_Key_ID = " + id;
//        return jdbcTemplate. //jaky executeUpdate();
//    };

    public List<Key> getAvailableKeys() {
        String query = "SELECT * FROM `door_key` \n" +
                "WHERE `Door_Key_ID` not in \n" +
                "(SELECT `Key_Key_ID` FROM `borrowing_status`\n" +
                "WHERE `Date_To` is null)";
        return jdbcTemplate.query(query, new KeyRowMapper());
    }

    public List<Key> getBorrowedKeys() {
        String query = "SELECT * FROM `door_key` \n" +
                "WHERE `Door_Key_ID` in \n" +
                "(SELECT `Key_Key_ID` FROM `borrowing_status`\n" +
                "WHERE `Date_To` IS NULL)";
        return jdbcTemplate.query(query, new KeyRowMapper());


    }


}
