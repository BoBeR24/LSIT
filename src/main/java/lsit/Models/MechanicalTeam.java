package lsit.Models;

public class MechanicalTeam {
    public String repairId;
    public String assessmentId;

    // Systems to be checked/repaired
    public boolean hullIntegrityCheck;
    public String hullDamageDetails;

    public boolean propulsionCheck;
    public String propulsionIssueDetails;

    public boolean lifeSupportCheck;
    public String lifeSupportIssueDetails;

    // Verification status
    public boolean mechanicalVerificationComplete;
    public String verificationNotes;

    public RepairStatus status;

    public enum RepairStatus {
        IN_PROGRESS,
        AWAITING_PARTS,
        COMPLETED
    }

}

