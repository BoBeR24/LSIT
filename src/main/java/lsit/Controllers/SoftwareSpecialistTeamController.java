package lsit.Controllers;

import lsit.Models.SoftwareSpecialist;
import lsit.Repositories.SoftwareSpecialistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/software-specialists")
public class SoftwareSpecialistTeamController {

    private final SoftwareSpecialistRepository softwareSpecialistRepository;

    @Autowired
    public SoftwareSpecialistTeamController(SoftwareSpecialistRepository softwareSpecialistRepository) {
        this.softwareSpecialistRepository = softwareSpecialistRepository;
    }

    // Leaving this API method commented out if someone wants to test adding new instances of entities.
    // Keep in mind that instance should be automatically created when user gets authorised
//    @PostMapping
//    public ResponseEntity<SoftwareSpecialist> createSoftwareSpecialist(@RequestBody SoftwareSpecialist softwareSpecialist) {
//        try {
//            softwareSpecialistRepository.add(softwareSpecialist);
//            return new ResponseEntity<>(softwareSpecialist, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/{id}")
    public ResponseEntity<SoftwareSpecialist> getSoftwareSpecialist(@PathVariable Integer id) {
        SoftwareSpecialist softwareSpecialist = softwareSpecialistRepository.get(id);
        if (softwareSpecialist != null) {
            return new ResponseEntity<>(softwareSpecialist, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<SoftwareSpecialist>> getAllSoftwareSpecialists() {
        try {
            List<SoftwareSpecialist> softwareSpecialists = softwareSpecialistRepository.list();
            return new ResponseEntity<>(softwareSpecialists, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SoftwareSpecialist> updateSoftwareSpecialist(@PathVariable Integer id, @RequestBody SoftwareSpecialist softwareSpecialist) {
        try {
            SoftwareSpecialist existingSoftwareSpecialist = softwareSpecialistRepository.get(id);
            if (existingSoftwareSpecialist == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            softwareSpecialistRepository.update(softwareSpecialist);
            return new ResponseEntity<>(softwareSpecialist, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoftwareSpecialist(@PathVariable Integer id) {
        try {
            SoftwareSpecialist existingSoftwareSpecialist = softwareSpecialistRepository.get(id);
            if (existingSoftwareSpecialist == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            softwareSpecialistRepository.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}