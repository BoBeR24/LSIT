package lsit.Controllers;

import lsit.Models.Assembler;
import lsit.Repositories.AssemblerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assemblers")
public class AssemblerTeamController {

    private final AssemblerRepository inMemoryAssemblerRepository;

    @Autowired
    public AssemblerTeamController(AssemblerRepository inMemoryAssemblerRepository) {
        this.inMemoryAssemblerRepository = inMemoryAssemblerRepository;
    }

    @PostMapping
    public ResponseEntity<Assembler> createAssembler(@RequestBody Assembler assembler) {
        try {
            inMemoryAssemblerRepository.add(assembler);
            return new ResponseEntity<>(assembler, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assembler> getAssembler(@PathVariable Integer id) {
        Assembler assembler = inMemoryAssemblerRepository.get(id);
        if (assembler != null) {
            return new ResponseEntity<>(assembler, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Assembler>> getAllAssemblers() {
        try {
            List<Assembler> assemblers = inMemoryAssemblerRepository.list();
            return new ResponseEntity<>(assemblers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assembler> updateAssembler(@PathVariable Integer id, @RequestBody Assembler assembler) {
        try {
            Assembler existingAssembler = inMemoryAssemblerRepository.get(id);
            if (existingAssembler == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            inMemoryAssemblerRepository.update(assembler);
            return new ResponseEntity<>(assembler, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssembler(@PathVariable Integer id) {
        try {
            Assembler existingAssembler = inMemoryAssemblerRepository.get(id);
            if (existingAssembler == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            inMemoryAssemblerRepository.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}