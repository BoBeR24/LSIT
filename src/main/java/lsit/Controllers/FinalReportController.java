package lsit.Controllers;

import lsit.Models.FinalReport;
import lsit.Repositories.FinalReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/final-reports")
public class FinalReportController {

    private final FinalReportRepository finalReportRepository;

    @Autowired
    public FinalReportController(FinalReportRepository finalReportRepository) {
        this.finalReportRepository = finalReportRepository;
    }

    @PostMapping
    public ResponseEntity<FinalReport> createFinalReport(@RequestBody FinalReport finalReport) {
        try {
            finalReportRepository.add(finalReport);
            return new ResponseEntity<>(finalReport, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinalReport> getFinalReport(@PathVariable String id) {
        FinalReport finalReport = finalReportRepository.get(id);
        if (finalReport != null) {
            return new ResponseEntity<>(finalReport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<FinalReport>> getAllFinalReports() {
        try {
            List<FinalReport> finalReports = finalReportRepository.list();
            return new ResponseEntity<>(finalReports, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinalReport> updateFinalReport(@PathVariable String id, @RequestBody FinalReport finalReport) {
        try {
            FinalReport existingFinalReport = finalReportRepository.get(id);
            if (existingFinalReport == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            finalReportRepository.update(finalReport);
            return new ResponseEntity<>(finalReport, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinalReport(@PathVariable String id) {
        try {
            FinalReport existingFinalReport = finalReportRepository.get(id);
            if (existingFinalReport == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            finalReportRepository.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}