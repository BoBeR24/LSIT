package lsit.Controllers;

import lsit.Models.DiagnosticAssessment;
import lsit.Models.DiagnosticAssessment.DamageLevel;
import lsit.Models.DiagnosticAssessment.ServiceTeam;
import lsit.Repositories.DiagnosticAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/diagnostic-assessments")
public class DiagnosticAssessmentController {

    private final DiagnosticAssessmentRepository diagnosticAssessmentRepository;

    @Autowired
    public DiagnosticAssessmentController(DiagnosticAssessmentRepository diagnosticAssessmentRepository) {
        this.diagnosticAssessmentRepository = diagnosticAssessmentRepository;
    }

    @PostMapping
    public ResponseEntity<DiagnosticAssessment> createAssessment(@RequestBody DiagnosticAssessment assessment) {
        try {
            if (assessment.id == null) {
                assessment.id = UUID.randomUUID();
            }
            diagnosticAssessmentRepository.add(assessment);
            return new ResponseEntity<>(assessment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticAssessment> getAssessment(@PathVariable UUID id) {
        DiagnosticAssessment assessment = diagnosticAssessmentRepository.get(id);
        if (assessment != null) {
            return new ResponseEntity<>(assessment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<DiagnosticAssessment>> getAllAssessments() {
        try {
            List<DiagnosticAssessment> assessments = diagnosticAssessmentRepository.list();
            return new ResponseEntity<>(assessments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiagnosticAssessment> updateAssessment(
            @PathVariable UUID id,
            @RequestBody DiagnosticAssessment assessment) {
        try {
            DiagnosticAssessment existingAssessment = diagnosticAssessmentRepository.get(id);
            if (existingAssessment == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            assessment.id = id;
            diagnosticAssessmentRepository.update(assessment);
            return new ResponseEntity<>(assessment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable UUID id) {
        try {
            DiagnosticAssessment existingAssessment = diagnosticAssessmentRepository.get(id);
            if (existingAssessment == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            diagnosticAssessmentRepository.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Specialized endpoints

//    @GetMapping("/by-request/{requestId}")
//    public ResponseEntity<List<DiagnosticAssessment>> getAssessmentsByRequestId(@PathVariable UUID requestId) {
//        try {
//            List<DiagnosticAssessment> assessments = diagnosticAssessmentRepository.list()
//                    .stream()
//                    .filter(a -> a.requestId == requestId)
//                    .collect(Collectors.toList());
//            return new ResponseEntity<>(assessments, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/by-team/{team}")
//    public ResponseEntity<List<DiagnosticAssessment>> getAssessmentsByTeam(
//            @PathVariable ServiceTeam team) {
//        try {
//            List<DiagnosticAssessment> assessments = diagnosticAssessmentRepository.list()
//                    .stream()
//                    .filter(a -> a.assignedTeam == team)
//                    .collect(Collectors.toList());
//            return new ResponseEntity<>(assessments, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/critical-damage")
//    public ResponseEntity<List<DiagnosticAssessment>> getAssessmentsWithCriticalDamage() {
//        try {
//            List<DiagnosticAssessment> assessments = diagnosticAssessmentRepository.list()
//                    .stream()
//                    .filter(a -> a.damagedParts.containsValue(DamageLevel.CRITICAL))
//                    .collect(Collectors.toList());
//            return new ResponseEntity<>(assessments, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/by-damage-level/{damageLevel}")
//    public ResponseEntity<List<DiagnosticAssessment>> getAssessmentsByDamageLevel(
//            @PathVariable DamageLevel damageLevel) {
//        try {
//            List<DiagnosticAssessment> assessments = diagnosticAssessmentRepository.list()
//                    .stream()
//                    .filter(a -> a.damagedParts.containsValue(damageLevel))
//                    .collect(Collectors.toList());
//            return new ResponseEntity<>(assessments, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PatchMapping("/{id}/team")
//    public ResponseEntity<DiagnosticAssessment> updateAssignedTeam(
//            @PathVariable UUID id,
//            @RequestBody ServiceTeam newTeam) {
//        try {
//            DiagnosticAssessment assessment = diagnosticAssessmentRepository.get(id);
//            if (assessment == null) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//            assessment.assignedTeam = newTeam;
//            diagnosticAssessmentRepository.update(assessment);
//            return new ResponseEntity<>(assessment, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}