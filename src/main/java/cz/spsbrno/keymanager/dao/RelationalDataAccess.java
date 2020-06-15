package cz.spsbrno.keymanager.dao;

import cz.spsbrno.keymanager.dto.Door;
import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import cz.spsbrno.keymanager.exception.InvalidOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.time.LocalDate;
import static java.time.LocalDate.*;

@Repository
public class RelationalDataAccess  {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User createUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("`user`")
                .usingGeneratedKeyColumns("User_ID");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("Name", user.getName());
        parameters.put("Surname", user.getSurname());

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

        return getUserById(id.intValue());
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM `user` WHERE User_ID = " + id;
        return jdbcTemplate.queryForObject(query, new UserRowMapper());
    }

    public Key createKey(Key key) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("`key`")
                .usingGeneratedKeyColumns("Key_ID");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("Number", key.getCode());

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

        return getKeyById(id.intValue());
    }

    public Key getKeyById(int id) {
        String query = "SELECT * FROM `key` WHERE Key_ID = " + id;
        return jdbcTemplate.queryForObject(query, new KeyRowMapper());
    }

    public void createBorrowingStatus(int userId, int keyId) {
        if(!isKeyAvailable(keyId)){
            throw new InvalidOperationException("key with id " + keyId + " is already borrowed");
        }
            SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("`borrowing_status`")
                    .usingGeneratedKeyColumns("Borrowing_Status_ID"); // ? - Takovy sloupec neexistuje
            Map<String, Object> params = new HashMap<>();
            params.put("User_User_ID", userId);
            params.put("Key_Key_ID", keyId);
            params.put("Date_From", new Timestamp(System.currentTimeMillis()));

            simpleJdbcInsert.execute(params);

    }

    private boolean isKeyAvailable(int keyId) {
        String query = "SELECT COUNT(*) FROM `borrowing_status` WHERE Key_Key_ID = ? AND Date_To IS NULL";
        Integer count = jdbcTemplate.queryForObject(query, new Object[] {keyId}, Integer.class);
        if (count >=1) {
            return false;
        }
        return true;
    }
    public List<Key> getAvailableKeys() {
        String query = "SELECT Key_ID, Number FROM `key` WHERE Borrowed = 0";
        List<Key> AvailableKeys = new ArrayList<>();
        List<Map<String,Object>> rows = jdbcTemplate.queryForList(query);
        for (Map row : rows){
            Key keyy = new Key();
            keyy.setId((Integer) row.get("Key_ID"));
            keyy.setCode((String) row.get("Number"));
            AvailableKeys.add(keyy);
        }
        return AvailableKeys;
    }
    public List<Key> getBorrowedKeysToUser(int userId) {
        List<Key> BorrowedKeys = new ArrayList<>();
        String query = "SELECT k.Key_ID, k.Number FROM `key` k, `borrowing_status` bs WHERE bs.Key_Key_ID = k.Key_ID AND bs.User_User_ID = "+ userId;
        List<Map<String,Object>> rows = jdbcTemplate.queryForList(query);
        for (Map row : rows){
            Key keyy = new Key();
            keyy.setId((Integer) row.get("Key_ID"));
            keyy.setCode((String) row.get("Number"));
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
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("`door`")
                .usingGeneratedKeyColumns("Door_ID");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("Number", door.getCode());

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

        return getDoorById(id.intValue());
    }

    public Door getDoorById(int doorId) {
        String query = "SELECT * FROM `door` WHERE Door_ID = " + doorId;
        return jdbcTemplate.queryForObject(query, new DoorRowMapper());
    }



    public void setDateToInBorrowingStatus(int keyId, int userId){
        String query = "UPDATE `borrowing_Status` SET Date_To = ? WHERE User_User_ID = ? AND Key_Key_ID = ? AND Date_To IS NULL";
        jdbcTemplate.update(query, new Timestamp(System.currentTimeMillis()), userId, keyId);

    }
    public void backupOfTheDatabase(){
        String execution = "BACKUP DATABASE `mydb` TO DISK = 'C:\\keymanagerbackup\\mydb.bak';"; //Predpripraveno - nevim, jak to implementovat spravne a zda to funguje
        jdbcTemplate.execute(execution);
    }
    public void restoreOfTheDatabase(){
        String execution = "RESTORE DATABASE `mydb` FROM DISK = 'C:\\keymanagerbackup\\mydb.bak';"; //Predpripraveno - nevim, jak to implementovat spravne tak, aby se to provedlo po kazdem zapnuti PC
        jdbcTemplate.execute(execution);
    }
}