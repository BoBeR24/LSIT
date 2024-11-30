package lsit.Controllers;

import lsit.Models.Electrician;
import lsit.Repositories.ElectricianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/electricians")
public class ElectricianTeamController {

    private final ElectricianRepository electricianRepository;

    @Autowired
    public ElectricianTeamController(ElectricianRepository electricianRepository) {
        this.electricianRepository = electricianRepository;
    }

    // Leaving this API method commented out if someone wants to test adding new instances of entities.
    // Keep in mind that instance should be automatically created when user gets authorised
//    @PostMapping
//    public ResponseEntity<Electrician> createElectrician(@RequestBody Electrician electrician) {
//        try {
//            electricianRepository.add(electrician);
//            return new ResponseEntity<>(electrician, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Electrician> getElectrician(@PathVariable Integer id) {
        Electrician electrician = electricianRepository.get(id);
        if (electrician != null) {
            return new ResponseEntity<>(electrician, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Electrician>> getAllElectricians() {
        try {
            List<Electrician> electricians = electricianRepository.list();
            return new ResponseEntity<>(electricians, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Electrician> updateElectrician(@PathVariable Integer id, @RequestBody Electrician electrician) {
        try {
            Electrician existingElectrician = electricianRepository.get(id);
            if (existingElectrician == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            electricianRepository.update(electrician);
            return new ResponseEntity<>(electrician, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElectrician(@PathVariable Integer id) {
        try {
            Electrician existingElectrician = electricianRepository.get(id);
            if (existingElectrician == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            electricianRepository.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}