package cz.spsbrno.keymanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ActionController {

    @GetMapping("/borrowedKeys")
    public String borrowedKeys() {
        return "borrowed-keys";
    }

    @GetMapping("keyBorrowHistory")
    public String keyBorrowHistory() {
        return "key-borrowHistory";
    }
}
