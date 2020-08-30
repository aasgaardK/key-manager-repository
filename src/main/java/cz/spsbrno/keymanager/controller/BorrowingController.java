package cz.spsbrno.keymanager.controller;

import cz.spsbrno.keymanager.dao.BorrowingDao;
import cz.spsbrno.keymanager.dao.KeyDao;
import cz.spsbrno.keymanager.dao.UserDao;
import cz.spsbrno.keymanager.dto.Borrowing;
import cz.spsbrno.keymanager.dto.BorrowingCreation;
import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/borrowings/{keyId}")
    public String borrowKey(@PathVariable int keyId,  Model model) {
        model.addAttribute("currentBorrowersList", this.borrowingDao.currentBorrowersByKey(keyId));
        model.addAttribute("borrowersList", this.borrowingDao.borrowersHistoryByKey(keyId));
        Key keyCode = new Key();
        Key key = this.keyDao.getKeyById(keyId);
        return "borrow-key-form";
    }

    @GetMapping("/borrowKeyForm")
    public String borrowKeyForm(BorrowingCreation borrowingCreation){
        return "borrow-key-form";
    }

    @PostMapping("/borrow")
    public String borrowKey(BorrowingCreation borrowingCreation, Model model){
        this.borrowingDao.borrowKey(Integer.parseInt(borrowingCreation.getUserId()), Integer.parseInt(borrowingCreation.getKeyId()));
        model.addAttribute("borrowedKeyList", this.keyDao.getBorrowedKeys());
        model.addAttribute("borrowedKeyMessage", "You have borrowed a key ");// + this.keyDao.getKeyById(keyId));
        return "borrowed-keys";
        //i would have added a {keyId} but don't know if it was right
        //method works in a db but feiles with the borrowed-keys template

    }

    @PostMapping("/key/{keyId}/return") //nebo GetMapping
    public String returnKey(@PathVariable int keyId, Model model){
        this.borrowingDao.finishBorrowing(keyId);
        model.addAttribute("borrowedKeyList",this.keyDao.getBorrowedKeys());
        model.addAttribute("returnedKeyMessage", "The key " + keyDao.getKeyById(keyId) + " was returned to the database");
        return "available-keys";
    }



}
