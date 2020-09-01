package cz.spsbrno.keymanager.controller;

import cz.spsbrno.keymanager.dao.BorrowingDao;
import cz.spsbrno.keymanager.dao.KeyDao;
import cz.spsbrno.keymanager.dao.UserDao;
import cz.spsbrno.keymanager.dto.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/borrowings")
public class BorrowingController {
    private final BorrowingDao borrowingDao;
    private final UserDao userDao;
    private final KeyDao keyDao;


    public BorrowingController(BorrowingDao borrowingDao, UserDao userDao, KeyDao keyDao) {
        this.borrowingDao = borrowingDao;
        this.userDao = userDao;
        this.keyDao = keyDao;
    }

    @GetMapping("/user/{userId}")
    public String borrowingsByUser(@PathVariable int userId, Model model) {
        model.addAttribute("currentBorrowingsList", this.borrowingDao.currentBorrowingsByUser(userId));
        model.addAttribute("borrowingsList", this.borrowingDao.borrowingsHistoryByUser(userId));
        User user = this.userDao.getUserById(userId);
        model.addAttribute("userMessage", "Borrowings for user " + user.getSurname() + " " + user.getName());
        return "borrowings-by-user";
    }

    @GetMapping("/key/{keyId}")
    public String borrowingsByKey(@PathVariable int keyId,  Model model) {
        model.addAttribute("currentBorrowersList", this.borrowingDao.currentBorrowersByKey(keyId));
        model.addAttribute("borrowersList", this.borrowingDao.borrowersHistoryByKey(keyId));
        Key key= this.keyDao.getKeyById(keyId);
        model.addAttribute("keyMessage", "Borrowers for key " + key.getCode());
        return "borrowings-by-key";
    }

    @GetMapping("/borrowKeyForm")
    public String borrowKeyForm(BorrowingCreation borrowingCreation, Model model) {
        List<UserFullName> usersWithFullName = this.userDao
                .getAll()
                .stream()
                .map(user -> new UserFullName(user.getId(), user.getName() + " " + user.getSurname()))
                .collect(Collectors.toList());


        model.addAttribute("userList", usersWithFullName);
        model.addAttribute("availableKeyList", this.keyDao.getAvailableKeys());
        return "borrow-key-form";
    }

    @PostMapping("/borrow")
    public String borrowKey(BorrowingCreation borrowingCreation, Model model){
        int keyId = Integer.parseInt(borrowingCreation.getKeyId());
        int userId = Integer.parseInt(borrowingCreation.getUserId());
        this.borrowingDao.borrowKey(userId, keyId);
        model.addAttribute("borrowedKeyList", this.keyDao.getBorrowedKeys());
        model.addAttribute("borrowedKeyMessage", "You have borrowed the key " + this.keyDao.getKeyById(keyId).getCode());
        return "borrowed-keys";

    }

    @GetMapping("/key/{keyId}/return")
    public String returnKey(@PathVariable int keyId, Model model){
        this.borrowingDao.finishBorrowing(keyId);
        model.addAttribute("availableKeyList",this.keyDao.getAvailableKeys());
        model.addAttribute("returnedKeyMessage", "The key " + keyDao.getKeyById(keyId).getCode() + " was returned");
        return "available-keys";
    }



}
