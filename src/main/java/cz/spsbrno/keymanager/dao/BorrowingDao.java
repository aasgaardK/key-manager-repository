package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.Borrowing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BorrowingDao {
    private JdbcTemplate jdbcTemplate;

    public BorrowingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void borrowKey(int userId, int keyId) {
        if(!isKeyAvailable(keyId)){
            return;
        }
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("Borrowing_Status")
                .usingGeneratedKeyColumns("Borrowing_Status_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("User_User_ID", userId);
        params.put("Key_Key_ID", keyId);
        params.put("Date_From", new Timestamp(System.currentTimeMillis()));

        simpleJdbcInsert.execute(params);

    }

    public void finishBorrowing(int keyId){
        String query = "UPDATE Borrowing_Status SET Date_To = ? WHERE Key_Key_ID = ? AND Date_To IS NULL";
        jdbcTemplate.update(query,LocalDateTime.now(), keyId);
    }

    private boolean isKeyAvailable(int keyId) {
        String query = "SELECT COUNT(*) FROM Borrowing_Status WHERE Key_Key_ID = ? AND Date_To IS NULL";
        Integer count = jdbcTemplate.queryForObject(query, new Object[]{keyId}, Integer.class);
        if (count >= 1) {
            return false;
        }
        return true;
    }

    public List<Borrowing> borrowingsHistoryByUser(int userId){
        String query = "SELECT\n" +
                "  *\n" +
                "FROM\n" +
                "  `borrowing_status` AS bs\n" +
                "JOIN `door_key` AS dk ON dk.`Door_Key_ID` = bs.`Key_Key_ID`\n" +
                "JOIN `user` AS u ON u.`User_ID` = bs.`User_User_ID`\n" +
                "WHERE\n" +
                "  `Date_To` IS NOT NULL AND `User_ID` = " + userId;
        return jdbcTemplate.query(query,new BorrowingRowMapper());
    }

    public List<Borrowing> currentBorrowingsByUser(int userId){
        String query = "SELECT\n" +
                "  *\n" +
                "FROM\n" +
                "  `borrowing_status` AS bs\n" +
                "JOIN `door_key` AS dk ON dk.`Door_Key_ID` = bs.`Key_Key_ID`\n" +
                "JOIN `user` AS u ON u.`User_ID` = bs.`User_User_ID`\n"+
                "WHERE\n" +
                "  `Date_To` IS NULL AND `User_ID` = " + userId;
        return jdbcTemplate.query(query, new BorrowingRowMapper());
    }

    public List<Borrowing> borrowersHistoryByKey(int keyId) {
        String query = "SELECT * FROM `borrowing_status` AS bs\n" +
                "JOIN `user`AS u ON u.`User_ID` = bs.`User_User_ID`\n" +
                "JOIN `door_key` AS dk ON `Door_Key_ID` = bs.`Key_Key_ID`\n" +
                "WHERE `Date_To` IS NOT NULL AND `Door_Key_ID` = " + keyId;
        return jdbcTemplate.query(query,new BorrowingRowMapper());
    }

    public List<Borrowing> currentBorrowersByKey(int keyId) {
        String query = "SELECT * FROM `borrowing_status` AS bs\n" +
                "JOIN `user`AS u ON u.`User_ID` = bs.`User_User_ID`\n" +
                "JOIN `door_key` AS dk ON `Door_Key_ID` = bs.`Key_Key_ID`\n" +
                "WHERE `Date_To` IS NULL AND `Door_Key_ID` = " + keyId;
        return jdbcTemplate.query(query, new BorrowingRowMapper());
    }


}
