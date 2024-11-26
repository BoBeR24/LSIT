package lsit.Models;

public class ElectricalTeam {
    public String repairId;
    public String assessmentId;

    // Systems to be checked/repaired
    public boolean powerSystemCheck;
    public String powerSystemIssueDetails;

    public boolean distributionCheck;
    public String distributionIssueDetails;

    public boolean emergencyBackupCheck;
    public String emergencyBackupIssueDetails;

    // Verification status
    public boolean electricalVerificationComplete;
    public String verificationNotes;

    public PowerSystemStatus status;


    public enum PowerSystemStatus {
        OPERATIONAL,
        DEGRADED,
        CRITICAL,
        OFFLINE
    }

}
