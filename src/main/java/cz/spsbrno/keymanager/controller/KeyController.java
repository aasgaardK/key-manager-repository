package cz.spsbrno.keymanager.controller;

import cz.spsbrno.keymanager.dao.RelationalDataAccess;
import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
//nemam sem inportovat jeste keymanager.dto.Door?
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/keys")
public class KeyController {
    private final RelationalDataAccess dao;

    public KeyController(RelationalDataAccess dao) {
        this.dao = dao;
    }

    /*
       vytvori klic
     */
    @PostMapping("/create") //magic upravuje kod via anotace
    public Key createKey(@RequestBody Key key){
        return dao.createKey(key);
    }

    /*
        ziska klic podle id
     */
    @GetMapping("/{keyId}")
    public Key getKeyById(@PathVariable int keyId) {
        Key key = dao.getKeyById(keyId);
        System.out.println(key);
        return key;
    }
}
