package cz.spsbrno.keymanager.controller;

import cz.spsbrno.keymanager.dao.DoorDao;
import cz.spsbrno.keymanager.dao.RelationalDataAccess;
import cz.spsbrno.keymanager.dto.Door;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doors")
public class DoorController {
    private final RelationalDataAccess dao;
    private final DoorDao doorDao;

    public DoorController(RelationalDataAccess dao, DoorDao doorDao) {
        this.dao = dao;
        this.doorDao = doorDao;
    }

    @PostMapping
    public String createDoor(Door door, Model model){
        Door createdDoor = doorDao.createDoor(door);
        model.addAttribute("createdDoorMessage", "New door " + createdDoor.getCode() + " was added to the database.");
        return getDoors(model);
    }

//    @GetMapping("/{doorId}")
//    public Door getDoorById(@PathVariable int doorId) {
//        Door door = dao.getDoorById(doorId);
//        System.out.println(door);
//        return door;
//    }

    @GetMapping
    public String getDoors(Model model){
       List<Door> doors = doorDao.getAll();
       model.addAttribute("doorList", doors);
       return "doors";
    }

    @GetMapping("/addDoorForm")
    public String addDoorForm(Door door){
        return "add-door";
    }


}