package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.Borrowing_Status;
import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import cz.spsbrno.keymanager.exception.InvalidOperationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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

//    public static List<Borrowing_Status> getAvailableKeys(){
//       String query = "SELECT Code FROM `door_key` AS dk LEFT JOIN " +
//               "`borrowing_status` AS bs ON dk.Door_Key_ID = bs.Borrowing_Status_ID" +
//                "WHERE bs.Date_To is NULL";
//        return jdbcTemplate.query(query, new BorrowingStatusRowMapper());
//    }

    public List<Borrowing_Status> getAll(){
        String query = "SELECT * FROM Borrowing_Status";
        return jdbcTemplate.query(query, new BorrowingStatusRowMapper());
    }

    public Borrowing_Status getBorrowingStatusById(int id) {
        String query = "SELECT * FROM `Borrowing_Status` WHERE Borrowing_Status_ID = " + id;
        return jdbcTemplate.queryForObject(query, new BorrowingStatusRowMapper());
    }
}
