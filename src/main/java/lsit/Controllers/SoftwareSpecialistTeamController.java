package lsit.Controllers;

import lsit.Models.SoftwareSpecialist;
import lsit.Repositories.SoftwareSpecialistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

    @PostMapping
    public ResponseEntity<SoftwareSpecialist> createSoftwareSpecialist(@RequestBody SoftwareSpecialist softwareSpecialist) {
        try {
            softwareSpecialistRepository.add(softwareSpecialist);
            return new ResponseEntity<>(softwareSpecialist, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoftwareSpecialist> getSoftwareSpecialist(@PathVariable Integer id,
                                                                    OAuth2AuthenticationToken authToken) {
        int authUserId = Integer.parseInt((String) authToken.getPrincipal().getAttributes().get("sub"));

        // If user tries to get access to a different user's data - refuse
        if (authUserId != id) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

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
    public ResponseEntity<SoftwareSpecialist> updateSoftwareSpecialist(@PathVariable Integer id,
                                                                       @RequestBody SoftwareSpecialist softwareSpecialist,
                                                                       OAuth2AuthenticationToken authToken) {
        try {
            int authUserId = Integer.parseInt((String) authToken.getPrincipal().getAttributes().get("sub"));

            // If user tries to get access to a different user's data - refuse
            if (authUserId != id) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

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
    public ResponseEntity<Void> deleteSoftwareSpecialist(@PathVariable Integer id, OAuth2AuthenticationToken authToken) {
        try {
            int authUserId = Integer.parseInt((String) authToken.getPrincipal().getAttributes().get("sub"));

            // If user tries to get access to a different user's data - refuse
            if (authUserId != id) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

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