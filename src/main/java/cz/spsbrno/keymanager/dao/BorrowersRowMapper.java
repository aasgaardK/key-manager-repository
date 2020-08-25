package cz.spsbrno.keymanager.dao;
import cz.spsbrno.keymanager.dto.Borrowing;
import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowersRowMapper implements RowMapper<Borrowing> {

    @Override
    public Borrowing mapRow(ResultSet rs, int rowNum) throws SQLException {
        Borrowing borrowers = new Borrowing();
        borrowers.setDateFrom(rs.getDate("Date_From"));//TODO time
        borrowers.setDateTo(rs.getDate("Date_To"));//TODO time
        Key key = new Key(rs.getInt("Key_Key_ID"), rs.getString("Code"));
        borrowers.setKey(key);
        User user = new User(rs.getInt("User_User:ID"), rs.getString("Name"), rs.getString("Surname"));
        borrowers.setUser(user);
        return borrowers;
    }

}
