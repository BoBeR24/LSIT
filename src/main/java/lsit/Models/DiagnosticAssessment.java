package lsit.Models;

import java.util.HashMap;

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
}
