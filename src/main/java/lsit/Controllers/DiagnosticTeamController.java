package lsit.Controllers;

import lsit.Models.Diagnostician;
import lsit.Repositories.DiagnosticianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Leaving this API method commented out if someone wants to test adding new instances of entities.
    // Keep in mind that instance should be automatically created when user gets authorised
//    @PostMapping
//    public ResponseEntity<Diagnostician> createDiagnostician(@RequestBody Diagnostician diagnostician) {
//        try {
//            diagnosticianRepository.add(diagnostician);
//            return new ResponseEntity<>(diagnostician, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Diagnostician> getDiagnostician(@PathVariable int id) {
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
    public ResponseEntity<Diagnostician> updateDiagnostician(@PathVariable int id, @RequestBody Diagnostician diagnostician) {
        try {
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
