package lsit.Controllers;

import lsit.Models.Electrician;
import lsit.Repositories.ElectricianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

    @PostMapping
    public ResponseEntity<Electrician> createElectrician(@RequestBody Electrician electrician) {
        try {
            electricianRepository.add(electrician);
            return new ResponseEntity<>(electrician, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Electrician> getElectrician(@PathVariable Integer id, OAuth2AuthenticationToken authToken) {
        int authUserId = Integer.parseInt((String) authToken.getPrincipal().getAttributes().get("sub"));

        // If user tries to get access to a different user's data - refuse
        if (authUserId != id) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

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
    public ResponseEntity<Electrician> updateElectrician(@PathVariable Integer id, @RequestBody Electrician electrician, OAuth2AuthenticationToken authToken) {
        try {
            int authUserId = Integer.parseInt((String) authToken.getPrincipal().getAttributes().get("sub"));

            // If user tries to get access to a different user's data - refuse
            if (authUserId != id) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

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
    public ResponseEntity<Void> deleteElectrician(@PathVariable Integer id, OAuth2AuthenticationToken authToken) {
        try {
            int authUserId = Integer.parseInt((String) authToken.getPrincipal().getAttributes().get("sub"));

            // If user tries to get access to a different user's data - refuse
            if (authUserId != id) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

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