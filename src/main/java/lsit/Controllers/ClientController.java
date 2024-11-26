package lsit.Controllers;

import lsit.Models.Client;
import lsit.Repositories.InMemoryIClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final InMemoryIClientRepository inMemoryClientRepository;

    @Autowired
    public ClientController(InMemoryIClientRepository inMemoryClientRepository) {
        this.inMemoryClientRepository = inMemoryClientRepository;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        try {
            inMemoryClientRepository.add(client);
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable UUID id) {
        Client client = inMemoryClientRepository.get(id);
        if (client != null) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        try {
            List<Client> clients = inMemoryClientRepository.list();
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable UUID id, @RequestBody Client client) {
        try {
            Client existingClient = inMemoryClientRepository.get(id);
            if (existingClient == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            client.id = id; // Ensure the ID matches the path variable
            inMemoryClientRepository.update(client);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        try {
            Client existingClient = inMemoryClientRepository.get(id);
            if (existingClient == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            inMemoryClientRepository.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}