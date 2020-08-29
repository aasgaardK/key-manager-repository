package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.Borrowing;
import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.exception.InvalidOperationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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
            throw new InvalidOperationException("key with id " + keyId + " is already borrowed");
        }
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("Borrowing_Status")
                .usingGeneratedKeyColumns("Borrowing_Status_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("User_User_ID", userId);
        params.put("Key_Key_ID", keyId);
        params.put("Date_From", new Timestamp(System.currentTimeMillis()));

        simpleJdbcInsert.execute(params);

        putKeyBackToPlace(keyId);
    }

    public void putKeyBackToPlace(int keyId){
        String query = "UPDATE Borrowing_Status SET Date_To = SYSDATETIME() WHERE Key_Key_ID = " + keyId;
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
