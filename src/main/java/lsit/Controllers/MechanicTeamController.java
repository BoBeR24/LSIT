package lsit.Controllers;

import lsit.Models.Mechanic;
import lsit.Repositories.MechanicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mechanics")
public class MechanicTeamController {

    private final MechanicRepository mechanicRepository;

    @Autowired
    public MechanicTeamController(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

    // Leaving this API method commented out if someone wants to test adding new instances of entities.
    // Keep in mind that instance should be automatically created when user gets authorised
//    @PostMapping
//    public ResponseEntity<Mechanic> createMechanic(@RequestBody Mechanic mechanic) {
//        try {
//            mechanicRepository.add(mechanic);
//            return new ResponseEntity<>(mechanic, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Mechanic> getMechanic(@PathVariable Integer id) {
        Mechanic mechanic = mechanicRepository.get(id);
        if (mechanic != null) {
            return new ResponseEntity<>(mechanic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Mechanic>> getAllMechanics() {
        try {
            List<Mechanic> mechanics = mechanicRepository.list();
            return new ResponseEntity<>(mechanics, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mechanic> updateMechanic(@PathVariable Integer id, @RequestBody Mechanic mechanic) {
        try {
            Mechanic existingMechanic = mechanicRepository.get(id);
            if (existingMechanic == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            mechanicRepository.update(mechanic);
            return new ResponseEntity<>(mechanic, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMechanic(@PathVariable Integer id) {
        try {
            Mechanic existingMechanic = mechanicRepository.get(id);
            if (existingMechanic == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            mechanicRepository.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}