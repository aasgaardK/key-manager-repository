package cz.spsbrno.keymanager.controller;

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
    private final UserDao userDao;

    public UserController( UserDao userDao) {
        this.userDao = userDao;
    }

    @PostMapping
    public String createUser(User user, Model model) {
        User createdUser = userDao.createUser(user);
        model.addAttribute("createdUserMessage",
                "New user " + createdUser.getName() + " " + createdUser.getSurname() + " was added to database.");
        return getUsers(model);
    }
//    @GetMapping("/delete")
//    public String deleteUser(User user, Model model) {
//        User deletedUser = userDao.deleteUser(user);
//        model.addAttribute("deletedUserMessage",
//                "User " + deletedUser.getName() + " " +deletedUser.getSurname()  + " was deleted from database. ");
//        return getUsers(model);
//    }

    @GetMapping("/addUserForm")
    public String addUserForm(User user){
        return "add-user";
    }

    @GetMapping("/deleteUserForm")
    public String deleteUserForm(User user){
        return "delete-user";
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        User user = userDao.getUserById(userId);
        return user;
    }

    @GetMapping
    public String getUsers(Model model){
        List<User> users = userDao.getAll();
        model.addAttribute("userList", users);
        return "users";
    }



}