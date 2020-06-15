package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.Door;
import cz.spsbrno.keymanager.dto.Key;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoorRowMapper implements RowMapper<Door>{
    @Override
    public Door mapRow(ResultSet rs, int rowNum) throws SQLException {
        Door door = new Door();

        door.setId(rs.getInt("Door_ID"));
        door.setCode(rs.getString("Code"));


        return door;
    }
}