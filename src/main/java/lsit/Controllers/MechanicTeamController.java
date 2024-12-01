package lsit.Controllers;

import lsit.Models.Mechanic;
import lsit.Repositories.MechanicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

    @PostMapping
    public ResponseEntity<Mechanic> createMechanic(@RequestBody Mechanic mechanic) {
        try {
            mechanicRepository.add(mechanic);
            return new ResponseEntity<>(mechanic, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mechanic> getMechanic(@PathVariable Integer id, OAuth2AuthenticationToken authToken) {
        int authUserId = Integer.parseInt((String) authToken.getPrincipal().getAttributes().get("sub"));

        // If user tries to get access to a different user's data - refuse
        if (authUserId != id) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

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
    public ResponseEntity<Mechanic> updateMechanic(@PathVariable Integer id, @RequestBody Mechanic mechanic, OAuth2AuthenticationToken authToken) {
        try {
            int authUserId = Integer.parseInt((String) authToken.getPrincipal().getAttributes().get("sub"));

            // If user tries to get access to a different user's data - refuse
            if (authUserId != id) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

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
    public ResponseEntity<Void> deleteMechanic(@PathVariable Integer id, OAuth2AuthenticationToken authToken) {
        try {
            int authUserId = Integer.parseInt((String) authToken.getPrincipal().getAttributes().get("sub"));

            // If user tries to get access to a different user's data - refuse
            if (authUserId != id) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

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