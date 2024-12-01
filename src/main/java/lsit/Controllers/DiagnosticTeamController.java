package lsit.Controllers;

import lsit.Models.Diagnostician;
import lsit.Repositories.DiagnosticianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diagnosticians")
public class DiagnosticTeamController {
    private final DiagnosticianRepository diagnosticianRepository;

    @Autowired
    public DiagnosticTeamController(DiagnosticianRepository diagnosticianRepository) {
        this.diagnosticianRepository = diagnosticianRepository;
    }

    @PostMapping
    public ResponseEntity<Diagnostician> createDiagnostician(@RequestBody Diagnostician diagnostician) {
        try {
            diagnosticianRepository.add(diagnostician);
            return new ResponseEntity<>(diagnostician, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Diagnostician> getDiagnostician(@PathVariable int id, OAuth2AuthenticationToken authToken) {
        int authUserId = Integer.parseInt((String) authToken.getPrincipal().getAttributes().get("sub"));

        // If user tries to get access to a different user's data - refuse
        if (authUserId != id) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Diagnostician diagnostician = diagnosticianRepository.get(id);
        if (diagnostician != null) {
            return new ResponseEntity<>(diagnostician, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Diagnostician>> getAllDiagnosticians() {
        try {
            List<Diagnostician> clients = diagnosticianRepository.list();
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Diagnostician> updateDiagnostician(@PathVariable int id, @RequestBody Diagnostician diagnostician, OAuth2AuthenticationToken authToken) {
        try {
            int authUserId = Integer.parseInt((String) authToken.getPrincipal().getAttributes().get("sub"));

            // If user tries to get access to a different user's data - refuse
            if (authUserId != id) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            Diagnostician existingClient = diagnosticianRepository.get(id);
            if (existingClient == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            diagnosticianRepository.update(diagnostician);
            return new ResponseEntity<>(diagnostician, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiagnostician(@PathVariable int id) {
        try {
            Diagnostician existingClient = diagnosticianRepository.get(id);
            if (existingClient == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            diagnosticianRepository.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
