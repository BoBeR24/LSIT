package lsit.Models;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class FinalReport {
    public String reportId;
    public String serviceRequestId;

    // Integration test results
    public boolean integrationTestPassed;
    public List<String> integrationIssues;

    // Cost breakdown
    public HashMap<CostCategory, Double> costBreakdown;
    public double totalCost;

    // Final status
    public ServiceStatus serviceStatus;
    public String finalNotes;
    public List<String> remainingIssues;
    public List<String> recommendations;

    public enum ServiceStatus {
        COMPLETED_ALL_CLEAR,
        COMPLETED_WITH_WARNINGS,
        COMPLETED_NEEDS_FOLLOWUP,
        FAILED_INTEGRATION
    }

    public enum CostCategory {
        PARTS,
        LABOR_MECHANICAL,
        LABOR_ELECTRICAL,
        LABOR_SOFTWARE,
        DIAGNOSTICS,
        INTEGRATION_TESTING,
        OTHER
    }

    public FinalReport(String serviceRequestId){
        this.serviceRequestId = serviceRequestId;

        this.integrationIssues = new ArrayList<>();
        this.costBreakdown = new HashMap<>();
        this.remainingIssues = new ArrayList<>();
        this.recommendations = new ArrayList<>();
    }


    public String generateCustomerSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Service Report Summary\n");
        summary.append("=====================\n");
        summary.append("Service ID: ").append(serviceRequestId).append("\n");
        summary.append("Status: ").append(serviceStatus).append("\n");
        summary.append("Total Cost: ").append(String.format("%.2f", totalCost)).append("\n\n");

        if (!remainingIssues.isEmpty()) {
            summary.append("Remaining Issues:\n");
            remainingIssues.forEach(issue -> summary.append("- ").append(issue).append("\n"));
            summary.append("\n");
        }

        if (!recommendations.isEmpty()) {
            summary.append("Recommendations:\n");
            recommendations.forEach(rec -> summary.append("- ").append(rec).append("\n"));
            summary.append("\n");
        }

        summary.append("Final Notes: ").append(finalNotes);

        return summary.toString();
    }
}
