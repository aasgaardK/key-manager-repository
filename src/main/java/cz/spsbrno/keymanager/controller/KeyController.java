package cz.spsbrno.keymanager.controller;

import cz.spsbrno.keymanager.dao.KeyDao;
import cz.spsbrno.keymanager.dto.Key;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/keys")
public class KeyController {
    private final KeyDao keyDao;

    public KeyController( KeyDao keyDao) {
        this.keyDao = keyDao;
    }

    @PostMapping
    public String createKey(Key key, Model model) {
        Key createdKey = keyDao.createKey(key);
        model.addAttribute("createdKeyMessage",
                "New key " + createdKey.getCode() + " was added to database. ");
        return getKeys(model);
    }

//    @GetMapping("/delete")
//    public String deleteKey(Key key, Model model) {
//        Key deletedKey = keyDao.deleteKey(key);
//        model.addAttribute("deletedKeyMessage",
//                "Key " + deletedKey.getCode() + " was deleted from database. ");
//        return getKeys(model);
//    }


    @GetMapping("/{keyId}")
    public Key getKeyById(@PathVariable int keyId) {
        Key key = keyDao.getKeyById(keyId); //TODO nefunguje
        System.out.println(key);
        return key;
    }

    @GetMapping("/available")
    public String getAvailableKeys(Model model){
        List<Key> availableKeys = this.keyDao.getAvailableKeys();
        model.addAttribute("availableKeyList",availableKeys);
        return "available-keys";
    }

    @GetMapping("/borrowed")
    public String getBorrowedKeys(Model model){
        List<Key> borrowedKeys = this.keyDao.getBorrowedKeys();
        model.addAttribute("borrowedKeyList", borrowedKeys);
        return "borrowed-keys";
    }

    @GetMapping("/addKeyForm")
    public String addKeyForm(Key key){
        return "add-key";
    }

    @GetMapping("/deleteKeyForm")
    public String deleteKeyForm(Key key){
        return "delete-key";
    }

    @GetMapping
    public String getKeys(Model model){
        List<Key> keys = keyDao.getAll();
        model.addAttribute("keyList", keys);
        return "keys";
    }
}