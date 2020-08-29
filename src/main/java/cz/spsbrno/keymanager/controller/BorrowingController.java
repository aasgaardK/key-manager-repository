package cz.spsbrno.keymanager.controller;

import cz.spsbrno.keymanager.dao.BorrowingDao;
import cz.spsbrno.keymanager.dao.KeyDao;
import cz.spsbrno.keymanager.dao.UserDao;
import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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


}
