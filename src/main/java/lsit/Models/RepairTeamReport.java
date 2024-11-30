package lsit.Models;

import lsit.SupportingClasses.ServiceTeam;

import java.util.List;
import java.util.UUID;

public class RepairTeamReport {
    public UUID reportId;
    public UUID serviceRequestId;

    public ServiceTeam serviceTeam;

    public List<String> solvedIssues;
    public List<String> remainingIssues;
    public String additionalNotes;
}
