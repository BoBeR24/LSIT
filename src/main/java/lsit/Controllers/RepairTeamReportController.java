package lsit.Controllers;

import lsit.Models.RepairTeamReport;
import lsit.Repositories.RepairTeamReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/repair-team-reports")
public class RepairTeamReportController {

    private final RepairTeamReportRepository repairTeamReportRepository;

    @Autowired
    public RepairTeamReportController(RepairTeamReportRepository repairTeamReportRepository) {
        this.repairTeamReportRepository = repairTeamReportRepository;
    }

    @PostMapping
    public ResponseEntity<RepairTeamReport> createRepairTeamReport(@RequestBody RepairTeamReport repairTeamReport) {
        try {
            repairTeamReportRepository.add(repairTeamReport);
            return new ResponseEntity<>(repairTeamReport, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepairTeamReport> getRepairTeamReport(@PathVariable UUID id) {
        RepairTeamReport repairTeamReport = repairTeamReportRepository.get(id);
        if (repairTeamReport != null) {
            return new ResponseEntity<>(repairTeamReport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<RepairTeamReport>> getAllRepairTeamReports() {
        try {
            List<RepairTeamReport> repairTeamReports = repairTeamReportRepository.list();
            return new ResponseEntity<>(repairTeamReports, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepairTeamReport> updateRepairTeamReport(@PathVariable UUID id, @RequestBody RepairTeamReport repairTeamReport) {
        try {
            RepairTeamReport existingRepairTeamReport = repairTeamReportRepository.get(id);
            if (existingRepairTeamReport == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            repairTeamReportRepository.update(repairTeamReport);
            return new ResponseEntity<>(repairTeamReport, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepairTeamReport(@PathVariable UUID id) {
        try {
            RepairTeamReport existingRepairTeamReport = repairTeamReportRepository.get(id);
            if (existingRepairTeamReport == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            repairTeamReportRepository.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}