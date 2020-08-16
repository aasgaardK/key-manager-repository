package cz.spsbrno.keymanager.controller;

import cz.spsbrno.keymanager.dao.RelationalDataAccess;
import cz.spsbrno.keymanager.dto.Door;
import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doors")
public class DoorController {
    private final RelationalDataAccess dao;

    public DoorController(RelationalDataAccess dao) {
        this.dao = dao;
    }

    @PostMapping("/create")
    public Door createDoor(@RequestBody Door door){
        return dao.createDoor(door);
    }

    @GetMapping("/{doorId}")
    public Door getDoorById(@PathVariable int doorId) {
        Door door = dao.getDoorById(doorId);
        System.out.println(door);
        return door;
    }

    @GetMapping
    public String getDoors(Model model){
        Door door1 = new Door(15,"Budova A");
        Door door2 = new Door(20,"Budova A");
        model.addAttribute("doors", new Door[]{door1, door2});
        return "doors";
    }
}