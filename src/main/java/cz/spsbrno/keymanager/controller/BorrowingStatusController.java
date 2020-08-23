package cz.spsbrno.keymanager.controller;

import cz.spsbrno.keymanager.dao.BorrowingStatusDao;
import cz.spsbrno.keymanager.dao.RelationalDataAccess;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/available-keys")
public class BorrowingStatusController {
    private final RelationalDataAccess dao;
    private final BorrowingStatusDao borrowingStatusDao;

    public BorrowingStatusController(RelationalDataAccess dao, BorrowingStatusDao borrowingStatusDao) {
        this.dao = dao;
        this.borrowingStatusDao = borrowingStatusDao;
    }

//    @PostMapping
//    public String borrowKey(Borrowing_Status borrowing_status, Model model){
//        Borrowing_Status borrowedKey = BorrowingStatusDao.borrowKey(borrowing_status);
//        model.addAttribute("borrowedKeyMessage", "Key " + Borrowing_Status.getCode() +
//                " was borrowed to user " + Borrowing_Status.getName + " " + Borrowing_Status.getSurname);
//        return getAvailableKeys(model);
//    }
//
//    @GetMapping
//    public String getAvailableKeys(Model model){
//        List<Borrowing_Status> availableKeys = BorrowingStatusDao.getAvailableKeys();
//        return null;
//    }
}


