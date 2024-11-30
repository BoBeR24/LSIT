package lsit.Controllers;

import lsit.Models.ServiceRequest;
import lsit.Repositories.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/service-requests")
public class ServiceRequestController {

    private final ServiceRequestRepository serviceRequestRepository;

    @Autowired
    public ServiceRequestController(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    @PostMapping
    public ResponseEntity<ServiceRequest> createServiceRequest(@RequestBody ServiceRequest request) {
        try {
            if (request.id == null) {
                request.id = UUID.randomUUID();
            }
            serviceRequestRepository.add(request);
            return new ResponseEntity<>(request, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequest> getServiceRequest(@PathVariable UUID id) {
        ServiceRequest request = serviceRequestRepository.get(id);
        if (request != null) {
            return new ResponseEntity<>(request, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ServiceRequest>> getAllServiceRequests() {
        try {
            List<ServiceRequest> requests = serviceRequestRepository.list();
            return new ResponseEntity<>(requests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceRequest> updateServiceRequest(
            @PathVariable UUID id,
            @RequestBody ServiceRequest request) {
        try {
            ServiceRequest existingRequest = serviceRequestRepository.get(id);
            if (existingRequest == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            serviceRequestRepository.update(request);
            return new ResponseEntity<>(request, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceRequest(@PathVariable UUID id) {
        try {
            ServiceRequest existingRequest = serviceRequestRepository.get(id);
            if (existingRequest == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            serviceRequestRepository.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}