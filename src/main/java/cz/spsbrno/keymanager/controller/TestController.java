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
    public Person getHello(){
        dao.createUser(new User(1, "n", "s"));
        Person person = new Person();
        person.name = "Kristinka";
        return person;
    }
    class Person {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
