package cz.spsbrno.keymanager.controller;

import cz.spsbrno.keymanager.dao.RelationalDataAccess;
import cz.spsbrno.keymanager.dto.User;
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

}
