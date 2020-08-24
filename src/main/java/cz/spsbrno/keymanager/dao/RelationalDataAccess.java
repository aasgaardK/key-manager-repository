package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.Door;
import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import cz.spsbrno.keymanager.exception.InvalidOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RelationalDataAccess  {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createBorrowingStatus(int userId, int keyId) {
        if(!isKeyAvailable(keyId)){
            throw new InvalidOperationException("key with id " + keyId + " is already borrowed");
        }
            SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("Borrowing_Status")
                    .usingGeneratedKeyColumns("Borrowing_Status_ID");
            Map<String, Object> params = new HashMap<>();
            params.put("User_User_ID", userId);
            params.put("Key_Key_ID", keyId);
            params.put("Date_From", new Timestamp(System.currentTimeMillis()));
            simpleJdbcInsert.execute(params);
    }

    private boolean isKeyAvailable(int keyId) {
        String query = "SELECT COUNT(*) FROM Borrowing_Status WHERE Key_Key_ID = ? AND Date_To IS NULL";
        Integer count = jdbcTemplate.queryForObject(query, new Object[] {keyId}, Integer.class);
        if (count >=1) {
            return false;
        }
        return true;
    }

    public void setDateToInBorrowingStatus(int keyId, int userId){
        String query = "UPDATE Borrowing_Status SET Date_To = ? WHERE User_User_ID = ? AND Key_Key_ID = ? AND Date_To IS NULL";
        jdbcTemplate.update(query, new Timestamp(System.currentTimeMillis()), userId, keyId);

    }

}