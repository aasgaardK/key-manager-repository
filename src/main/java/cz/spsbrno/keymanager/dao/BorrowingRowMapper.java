package cz.spsbrno.keymanager.dao;


import cz.spsbrno.keymanager.dto.Borrowing;
import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowingRowMapper implements RowMapper<Borrowing> {

    @Override
    public Borrowing mapRow(ResultSet rs, int rowNum) throws SQLException {
        Borrowing borrowing = new Borrowing();
        borrowing.setDateFrom(rs.getDate("Date_From")); //TODO time
        borrowing.setDateTo(rs.getDate("Date_To"));
        User user = new User(rs.getInt("User_User_ID"), rs.getString("Name"), rs.getString("Surname"));
        borrowing.setUser(user);
        Key key = new Key(rs.getInt("Key_Key_ID"), rs.getString("Code"));
        borrowing.setKey(key);
        return borrowing;
    }
}
