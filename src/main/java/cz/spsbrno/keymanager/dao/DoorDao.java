package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.Door;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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


}
