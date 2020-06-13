package cz.spsbrno.keymanager.controller;

import cz.spsbrno.keymanager.dao.RelationalDataAccess;
import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final RelationalDataAccess dao;

    public UserController(RelationalDataAccess dao) {
        this.dao = dao;
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return dao.createUser(user);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        User user = dao.getUserById(userId);
        //System.out.println(user);

        return user;
    }
    @GetMapping("/{userId}")
    public String getBorrowedKeysToUser(@PathVariable int userId){
        List<Key> outList = dao.getBorrowedKeysToUser(userId);
        String out = "All borrowed keys by this user: \n";
        for (Key key : outList){
            String id = Integer.toString(key.getId());
            out += "Next borrowed key by this user: ID of the key: " + id + ", code of the key: " + key.getCode() + "\n";
        }
        return out;
    }

    @PostMapping("/{userId}/keys/borrow/{keyId}")
    public void borrowKey(@PathVariable int userId, @PathVariable int keyId) {
        dao.createBorrowingStatus(userId, keyId);
    }

    /*
        pozor, zde důležitá poznámka. Zde jsem setDateToInBorrowingStatus připravila
        určitým stylem, ale konečnou úpravu budeš muset provést v závislosti na tom,
        jaké tam Emma dá endpoits.
        Pokud v Templates např bude i endpoit pro tuto funkci, tak se zde tento kód nemusí takto používat.
        Ten jsem sem dala jen pro případ, že by Emma v Template na to nemyslela,
        aby se to dalo nějak vyvolat z aplikace.
        Pokud tam budou vstupy, tak to musíš upravit podel nich
     */
    @PostMapping("/{userId}/keys/return/{keyId}")
    public void returnKey(@PathVariable int userId, @PathVariable int keyId) {
        dao.setDateToInBorrowingStatus(keyId, userId);
    }
}