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

    /*
       vytvori dvere
     */
    @PostMapping("/create") //magic upravuje kod via anotace
    public Door createDoor(@RequestBody Door door){
        return dao.createDoor(door);
    }

    /*
        ziska dvere podle id
     */
    @GetMapping("/{doorId}")
    public Door getDoorById(@PathVariable int doorId) {
        Door door = dao.getDoorById(doorId);
        System.out.println(door);
        return door;
    }
}
