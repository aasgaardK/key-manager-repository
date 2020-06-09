package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.Key;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KeyRowMapper implements RowMapper<Key> {

    @Override
    public Key mapRow(ResultSet rs, int rowNum) throws SQLException {
        Key key = new Key();

        key.setId(rs.getInt("Door_Key_ID"));
        key.setCode(rs.getString("Code"));


        return key;
    }
}
