package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getAll(){
        String query = "SELECT * FROM user";
        return jdbcTemplate.query(query, new UserRowMapper());
    }

    public User createUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("User")
                .usingGeneratedKeyColumns("User_ID");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("Name", user.getName());
        parameters.put("Surname", user.getSurname());

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

        return getUserById(id.intValue());
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM `user` WHERE User_ID = " + id;
        return jdbcTemplate.queryForObject(query, new UserRowMapper());
    }



}
