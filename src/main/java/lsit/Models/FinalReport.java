package lsit.Models;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class FinalReport {
    public String finalReportId;
    public String serviceRequestId;

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
        OTHER
    }

}
