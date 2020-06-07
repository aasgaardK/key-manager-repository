package cz.spsbrno.keymanager.controller;

import cz.spsbrno.keymanager.dao.RelationalDataAccess;
import cz.spsbrno.keymanager.dto.Door;
import cz.spsbrno.keymanager.dto.Key;
import cz.spsbrno.keymanager.dto.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doors")
public class DoorController {
    private final RelationalDataAccess dao;

    public DoorController(RelationalDataAccess dao) {
        this.dao = dao;
    }

    @PostMapping("/create") //magic upravuje kod via anotace
    public Door createDoor(@RequestBody Door door){
        return dao.createDoor(door);
    }

    @GetMapping("/{doorId}")
    public Door getDoorById(@PathVariable int doorId) {
        Door door = dao.getDoorById(doorId);
        System.out.println(door);
        return door;
    }
}
