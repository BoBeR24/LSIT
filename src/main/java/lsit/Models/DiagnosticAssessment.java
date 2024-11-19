package lsit.Models;

import java.util.HashMap;
import java.util.Map;

public class DiagnosticAssessment {
    public String assessmentId;
    public String requestId;
    
    public ServiceTeam assignedTeam;

    // Issues
    public HashMap<String, DamageLevel> damagedParts;

    public String additionalCommentary;


    public enum DamageLevel {
        LOW, SEVERE, CRITICAL
    }

    public enum ServiceTeam {
        MECHANICAL, ELECTRICAL, SOFTWARE
    }

    public DiagnosticAssessment(String assessmentId,String requestId){
        this.assessmentId=assessmentId;
        this.requestId=requestId;
        this.damagedParts=new HashMap<>();
    }
    public String getAssessmentId(){
        return requestId;
    }
    public void setRequestId(String requestId){
        this.requestId=requestId;
    }
    public ServiceTeam getAssignedTeam(){
        return assignedTeam;
    }
    public void setAssignedTeam(ServiceTeam assignedTeam){
        this.assignedTeam=assignedTeam;
    }
    public Map<String,DamageLevel> getDamagedParts(){
        return damagedParts;
    }
    public void addDamagedPart(String partName, DamageLevel level){
        this.damagedParts.put(partName,level);
    }
    public void setDamagedParts(HashMap<String, DamageLevel> damagedParts) {
        this.damagedParts = damagedParts;
    }
    public String getAdditionalCommentary(){
        return additionalCommentary;
    }
    public void setAdditionalCommentary(String additionalCommentary){
        this.additionalCommentary=additionalCommentary;
    }
    // Assign a team 
    public void evaAndAssign() {
        if (damagedParts.containsValue(DamageLevel.CRITICAL)) {
            this.assignedTeam = ServiceTeam.MECHANICAL;
        } else if (damagedParts.containsValue(DamageLevel.SEVERE)) {
            this.assignedTeam = ServiceTeam.ELECTRICAL;
        } else {
            this.assignedTeam = ServiceTeam.SOFTWARE;
        }
    }

    public String report(){
        StringBuilder report=new StringBuilder();
        report.append("Assessment ID: ").append(assessmentId).append("\n");
        report.append("Request ID: ").append(requestId).append("\n");
        report.append("Assigned Team: ").append(assignedTeam).append("\n");
        report.append("Damaged parts: ");
        for (Map.Entry<String, DamageLevel> entry : damagedParts.entrySet()) {
            report.append(" - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        if (additionalCommentary != null && !additionalCommentary.isEmpty()) {
            report.append("Additional Commentary: ").append(additionalCommentary).append("\n");
        }
        return report.toString();

    }

}
