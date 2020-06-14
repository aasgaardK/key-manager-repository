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

}