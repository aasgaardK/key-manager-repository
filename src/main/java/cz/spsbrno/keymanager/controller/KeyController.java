package cz.spsbrno.keymanager.controller;

import cz.spsbrno.keymanager.dao.RelationalDataAccess;
import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.util.List;

@RestController
@RequestMapping("/keys")
public class KeyController {
    private final RelationalDataAccess dao;

    public KeyController(RelationalDataAccess dao) {
        this.dao = dao;
    }

    @PostMapping("/create")
    public Key createKey(@RequestBody Key key) {
        return dao.createKey(key);
    }

    @GetMapping("/{keyId}")
    public Key getKeyById(@PathVariable int keyId) {
        Key key = dao.getKeyById(keyId);
        System.out.println(key);
        return key;
    }
    @GetMapping()
    public String getAvailableKeys(){
        List<Key> outList = dao.getAvailableKeys();
        String out = "All available keys: \n";
        for (Key key : outList){
            String id = Integer.toString(key.getId());
            out += "Next available key: ID of the key: " + id + ", code of the key: " + key.getCode() + "\n";
        }
        return out;
    }
    @GetMapping("/{keyId}")
    public String getBorrowingUsersByKey(@PathVariable int keyId){
        List<User> outList = dao.getBorrowingUsersByKey(keyId);
        String out = "All borrowing users for this specific user: \n";
        for (User user : outList){
            String id = Integer.toString(user.getId());
            out += "Next borrowing user of this key: ID of the user: " + id + ", name of the user: " + user.getName() + ", surname of the user: "+user.getSurname() + "\n" ;
        }
        return out;
    }

}