package lsit.Models;

public class SoftwareTeam {
    public String repairId;
    public String assessmentId;

    // Systems to be checked/repaired
    public boolean navigationAICheck;
    public String navigationIssueDetails;

    public boolean systemOSCheck;
    public String systemOSIssueDetails;

    public boolean securityProtocolCheck;
    public String securityIssueDetails;

    // Verification status
    public boolean softwareVerificationComplete;
    public String verificationNotes;

    public SoftwareUpdateStatus status;

    public enum SoftwareUpdateStatus {
        PENDING,
        INSTALLING,
        TESTING,
        COMPLETED
    }



}
