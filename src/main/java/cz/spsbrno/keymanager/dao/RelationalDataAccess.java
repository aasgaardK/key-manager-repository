package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RelationalDataAccess {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    /*
        dostane user bez id jako parametr, vytvo59 ho v datab8i, a vr8t9 vztvorenz objekt y databyae i s id
     */
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
        String query = "SELECT * FROM User WHERE User_ID = " + id;
        return jdbcTemplate.queryForObject(query, new UserRowMapper());
    }
}
