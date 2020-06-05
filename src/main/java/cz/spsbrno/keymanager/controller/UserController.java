package cz.spsbrno.keymanager.controller;

import cz.spsbrno.keymanager.dao.RelationalDataAccess;
import cz.spsbrno.keymanager.dto.User;
//nemam sem importovat jeste keymanager.dto.Key a Door?
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController
{
    private final RelationalDataAccess dao;

    public UserController(RelationalDataAccess dao) {
        this.dao = dao;
    }

    /*
        vytvori uzivatele
     */
    @PostMapping
    public User createUser(@RequestBody User user){
        return dao.createUser(user);
    }

    /*
        ziska uzivatele podle id
     */
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        User user = dao.getUserById(userId);
        System.out.println(user);

        return user;
    }

    @PostMapping("/{userId}/keys/borrow/{keyId}")
    public void borrowKey(@PathVariable int userId, @PathVariable int keyId){
        dao.createBorrowingStatus(userId, keyId);


    }

}
