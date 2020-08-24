package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.exception.InvalidOperationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BorrowingStatusDao {
    private JdbcTemplate jdbcTemplate;

    public BorrowingStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void borrowKey(int userId, int keyId) {
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
        Integer count = jdbcTemplate.queryForObject(query, new Object[]{keyId}, Integer.class);
        if (count >= 1) {
            return false;
        }
        return true;
    }

}
