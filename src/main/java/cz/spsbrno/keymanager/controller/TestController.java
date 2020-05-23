package cz.spsbrno.keymanager.controller;

import cz.spsbrno.keymanager.dao.RelationalDataAccess;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController
{
    private final RelationalDataAccess dao;

    public TestController(RelationalDataAccess dao) {
        this.dao = dao;
    }

    @GetMapping("/hello")
    public String getHello(){
        User u = dao.createUser(new User(1, "n", "s"));
        System.out.println(u);
        return "test";
    }




}
