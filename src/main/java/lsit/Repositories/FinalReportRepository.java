package lsit.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lsit.Models.FinalReport;
import org.springframework.stereotype.Repository;

@Repository
public class FinalReportRepository {
    static HashMap<String, FinalReport> finalReports = new HashMap<String, FinalReport>();

    public void add(FinalReport finalReport){
        finalReports.put(finalReport.finalReportId, finalReport);
    }

    public FinalReport get(String id){
        return finalReports.get(id);
    }

    public void remove(String id){
        finalReports.remove(id);
    }

    public void update(FinalReport finalReport){
        FinalReport x = finalReports.get(finalReport.finalReportId);
        x.serviceRequestId = finalReport.serviceRequestId;
        x.costBreakdown = finalReport.costBreakdown;
        x.totalCost = finalReport.totalCost;
        x.serviceStatus = finalReport.serviceStatus;
        x.finalNotes = finalReport.finalNotes;
        x.remainingIssues = finalReport.remainingIssues;
        x.recommendations = finalReport.recommendations;
    }

    public List<FinalReport> list(){
        return new ArrayList<>(finalReports.values());
    }
}