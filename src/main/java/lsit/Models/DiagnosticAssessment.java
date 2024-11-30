package lsit.Models;

import lsit.SupportingClasses.ServiceTeam;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DiagnosticAssessment {
    public UUID id;
    public UUID requestId;
    public List<Integer> authorsId;

    public ServiceTeam assignedTeam;

    // Issues
    public HashMap<String, DamageLevel> damagedParts;

    public String additionalCommentary;


    public enum DamageLevel {
        LOW, SEVERE, CRITICAL
    }


}
