package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.Borrowing_Status;
import cz.spsbrno.keymanager.dto.Door;
import cz.spsbrno.keymanager.dto.Key;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowingStatusRowMapper implements RowMapper<Borrowing_Status>{

    @Override
    public Borrowing_Status mapRow(ResultSet rs, int rowNum) throws SQLException {
        Borrowing_Status borrowing_status = new Borrowing_Status();
        borrowing_status.setBorrowing_status_id(rs.getInt("Borrowing_Status_ID"));
        borrowing_status.setKey_key_id(rs.getInt("Key_Key_ID"));
        borrowing_status.setUser_user_id(rs.getInt("User_User_ID"));
        borrowing_status.setDate_from(rs.getDate("Date_From"));
        borrowing_status.setDate_to(rs.getDate("Date_To"));
        return borrowing_status;
    }
}
