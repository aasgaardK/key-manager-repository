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





    public Key createKey(Key key) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("Door_Key")
                .usingGeneratedKeyColumns("Door_Key_ID");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("Code", key.getCode());

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

        return getKeyById(id.intValue());
    }

    public Key getKeyById(int id) {
        String query = "SELECT * FROM `door_key` WHERE Door_Key_ID = " + id;
        return jdbcTemplate.queryForObject(query, new KeyRowMapper());
    }

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
    public List<Key> getAvailableKeys() {
        String query = "SELECT k.Door_Key_ID, k.Code FROM `door_key` k, `borrowing_status` bs WHERE bs.Key_Key_ID = k.Door_Key_ID";
        List<Key> AvailableKeys = new ArrayList<>();
        List<Map<String,Object>> rows = jdbcTemplate.queryForList(query);
        for (Map row : rows){
            Key keyy = new Key();
            keyy.setId((Integer) row.get("Key_ID"));
            keyy.setCode((String) row.get("Code"));
            AvailableKeys.add(keyy);
        }
        return AvailableKeys;
    }
    public List<Key> getBorrowedKeysToUser(int userId) {
        List<Key> BorrowedKeys = new ArrayList<>();
        String query = "SELECT k.Door_Key_ID, k.Code FROM `door_key` k, `borrowing_status` bs WHERE bs.Key_Key_ID = k.Door_Key_ID AND bs.User_User_ID = "+ userId;
        List<Map<String,Object>> rows = jdbcTemplate.queryForList(query);
        for (Map row : rows){
            Key keyy = new Key();
            keyy.setId((Integer) row.get("Key_ID"));
            keyy.setCode((String) row.get("Code"));
            BorrowedKeys.add(keyy);
        }
        return BorrowedKeys;
    }

    public List<User> getBorrowingUsersByKey(int keyId){
        List<User> BorrowingUsers = new ArrayList<>();
        String query = "SELECT u.User_ID, u.Name, u.Surname FROM `user` u, `borrowing_status` bs WHERE bs.User_User_ID = u.User_ID AND bs.Key_Key_ID = "+ keyId;
        List<Map<String,Object>> rows = jdbcTemplate.queryForList(query);
        for (Map row : rows){
            User userr = new User();
            userr.setId((Integer) row.get("User_ID"));
            userr.setName((String) row.get("Name"));
            userr.setSurname((String) row.get("Surname"));
            BorrowingUsers.add(userr);
        }
        return BorrowingUsers;
    }

    public Door createDoor(Door door) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("Door`")
                .usingGeneratedKeyColumns("Door_ID");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("Code", door.getCode());

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

        return getDoorById(id.intValue());
    }

    public Door getDoorById(int doorId) {
        String query = "SELECT * FROM Door WHERE Door_ID = " + doorId;
        return jdbcTemplate.queryForObject(query, new DoorRowMapper());
    }



    public void setDateToInBorrowingStatus(int keyId, int userId){
        String query = "UPDATE Borrowing_Status SET Date_To = ? WHERE User_User_ID = ? AND Key_Key_ID = ? AND Date_To IS NULL";
        jdbcTemplate.update(query, new Timestamp(System.currentTimeMillis()), userId, keyId);

    }
    // @EventListener(ApplicationReadyEvent.class) -> Spousteni funkce, pred kterou je toto dano, pri startu
}