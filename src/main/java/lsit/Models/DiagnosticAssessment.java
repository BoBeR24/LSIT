package lsit.Models;

import java.util.HashMap;
import java.util.UUID;

public class DiagnosticAssessment {
    public UUID id;
    public UUID requestId;

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
