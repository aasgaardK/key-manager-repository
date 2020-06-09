package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("User_ID"));
        user.setName(rs.getString("Name"));
        user.setSurname(rs.getString("Surname"));

        return user;
    }
}
