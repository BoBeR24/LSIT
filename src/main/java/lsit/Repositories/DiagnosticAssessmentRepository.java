package lsit.Repositories;

import lsit.Models.DiagnosticAssessment;
import lsit.Models.Pet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class DiagnosticAssessmentRepository {
    static HashMap<UUID, DiagnosticAssessment> diagnosticAssessments = new HashMap<>();

    public void add(DiagnosticAssessment diagnosticAssessment){
        diagnosticAssessments.put(diagnosticAssessment.id, diagnosticAssessment);
    }

    public DiagnosticAssessment get(UUID id){
        return diagnosticAssessments.get(id);
    }

    public void remove(UUID id){
        diagnosticAssessments.remove(id);
    }

    public void update(DiagnosticAssessment newAssessment){
        DiagnosticAssessment x = diagnosticAssessments.get(newAssessment.id);
        x.requestId = newAssessment.requestId;
        x.assignedTeam = newAssessment.assignedTeam;

        x.additionalCommentary = newAssessment.additionalCommentary;
    }

    public List<DiagnosticAssessment> list(){
        return new ArrayList<>(diagnosticAssessments.values());
    }
}
