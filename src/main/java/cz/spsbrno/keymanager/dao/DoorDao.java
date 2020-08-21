package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.Door;
import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DoorDao {
    private JdbcTemplate jdbcTemplate;

    public DoorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Door> getAll(){
        String query = "SELECT * FROM door";
        return jdbcTemplate.query(query, new DoorRowMapper());
    }

    public Door createDoor(Door door) {
        DataSource dataSource;
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("door")
                .usingGeneratedKeyColumns("Door_ID");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("Code", door.getCode());

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

        return getDoorById(id.intValue());
    }

    public Door getDoorById(int id) {
        String query = "SELECT * FROM `door` WHERE Door_ID = " + id;
        return jdbcTemplate.queryForObject(query, new DoorRowMapper());
    }


}
