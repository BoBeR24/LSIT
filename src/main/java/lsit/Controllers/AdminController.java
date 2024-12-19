package lsit.Controllers;

import lsit.Models.Admin;
import lsit.Repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        try {
            adminRepository.add(admin);
            return new ResponseEntity<>(admin, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdmin(@PathVariable int id, OAuth2AuthenticationToken authToken) {
        Admin admin = adminRepository.get(id);
        if (admin != null) {
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping
//    public ResponseEntity<List<Admin>> getAllAdmins() {
//        try {
//            List<Admin> admins = adminRepository.list();
//            return new ResponseEntity<>(admins, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Admin> updateAdmin(@PathVariable int id, @RequestBody Admin admin, OAuth2AuthenticationToken authToken) {
//        try {
//            Admin existingAdmin = adminRepository.get(id);
//            if (existingAdmin == null) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//
//            adminRepository.update(admin);
//            return new ResponseEntity<>(admin, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteAdmin(@PathVariable int id, OAuth2AuthenticationToken authToken) {
//        try {
//            Admin existingAdmin = adminRepository.get(id);
//            if (existingAdmin == null) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//            adminRepository.remove(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
