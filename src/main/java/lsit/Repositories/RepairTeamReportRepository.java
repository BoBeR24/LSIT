package lsit.Repositories;

import lsit.Models.RepairTeamReport;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RepairTeamReportRepository {

    private final Map<UUID, RepairTeamReport> reports = new HashMap<>();

    // Add a new report
    public void add(RepairTeamReport report) {
        reports.put(report.reportId, report);
    }

    // Get a report by ID
    public RepairTeamReport get(UUID reportId) {
        return reports.get(reportId);
    }

    // Remove a report by ID
    public void remove(UUID reportId) {
        reports.remove(reportId);
    }

    // Update an existing report
    public void update(RepairTeamReport updatedReport) {
        RepairTeamReport report = reports.get(updatedReport.reportId);

        report.serviceRequestId = updatedReport.serviceRequestId;

        report.serviceTeam = updatedReport.serviceTeam;

        report.solvedIssues = updatedReport.solvedIssues;
        report.remainingIssues = updatedReport.remainingIssues;

        report.additionalNotes = updatedReport.additionalNotes;
    }

    // List all reports
    public List<RepairTeamReport> list() {
        return new ArrayList<>(reports.values());
    }
}
