package cz.spsbrno.keymanager.controller;

import cz.spsbrno.keymanager.dao.RelationalDataAccess;
import cz.spsbrno.keymanager.dao.UserDao;
import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final RelationalDataAccess dao;
    private final UserDao userDao;

    public UserController(RelationalDataAccess dao, UserDao userDao) {
        this.dao = dao;
        this.userDao = userDao;
    }

    @PostMapping
    public String createUser(User user, Model model) {
        User createdUser = userDao.createUser(user);
        model.addAttribute("createdUserMessage",
                "New user " + createdUser.getName() + " " + createdUser.getSurname() + " was added to database.");
        return getUsers(model);
    }

    @GetMapping("/addUserForm")
    public String addUserForm(User user){
        return "add-user";
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        User user = userDao.getUserById(userId);
        return user;
    }
//    @GetMapping("/borrke/{userId}")
//    public String getBorrowedKeysToUser(@PathVariable int userId){
//        List<Key> outList = dao.getBorrowedKeysToUser(userId);
//    String out = "All borrowed keys by this user: <br />";
//        for (Key key : outList){
//        String id = Integer.toString(key.getId());
//        out += "Next borrowed key by this user: ID of the key: " + id + ", code of the key: " + key.getCode() + "<br />";
//    }
//        return out;
//}

    @PostMapping("/{userId}/keys/borrow/{keyId}")
    public void borrowKey(@PathVariable int userId, @PathVariable int keyId) {
        dao.createBorrowingStatus(userId, keyId);

    }

    @PostMapping("/{userId}/keys/return/{keyId}")
    public void returnKey(@PathVariable int userId, @PathVariable int keyId) {
        dao.setDateToInBorrowingStatus(keyId, userId);
    }

    @GetMapping
    public String getUsers(Model model){
        List<User> users = userDao.getAll();
        model.addAttribute("userList", users);
        return "users";
    }



}