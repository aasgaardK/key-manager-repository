package cz.spsbrno.keymanager.controller;

import cz.spsbrno.keymanager.dao.BorrowingDao;
import cz.spsbrno.keymanager.dao.KeyDao;
import cz.spsbrno.keymanager.dao.UserDao;
import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/user")
    public String borrowingsByUser(Model model) {
        model.addAttribute("currentBorrowingsList", this.borrowingDao.currentBorrowingsByUser(1));
        model.addAttribute("borrowingsList", this.borrowingDao.borrowingsHistoryByUser(1));
        User user1 = this.userDao.getUserById(1); //TODO user id from path parameter
        model.addAttribute("userMessage", "Borrowings for user " + user1.getSurname() + " " + user1.getName());
        return "borrowings-by-user";
    }

    @GetMapping("/key") //TODO fix vsechno s key - z domaciho ukolu - nefunguje to a nevim kde je chyba
    public String borrowersByKey(Model model) {
       // Borrowing bor1 =new Borrowing(new Key(1111,"c1111"),
        model.addAttribute("currentBorrowersList", this.borrowingDao.currentBorrowersByKey(1));
        model.addAttribute("borrowersList", this.borrowingDao.borrowersHistoryByKey(1));
        Key key1 = this.keyDao.getKeyById(1);//TODO keu from parameter
        model.addAttribute("keyMessage", "Borrowers for key " + key1.getCode());
//                new User(1111, "Pavel", "Pavlovic"),new Date(System.currentTimeMillis()),
//                new Date(System.currentTimeMillis()));
        return "borrowings-by-key";
    }


}
