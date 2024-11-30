package lsit.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lsit.Models.SoftwareSpecialist;
import org.springframework.stereotype.Repository;

@Repository
public class SoftwareSpecialistRepository {
    static HashMap<Integer, SoftwareSpecialist> softwareSpecialists = new HashMap<Integer, SoftwareSpecialist>();

    public void add(SoftwareSpecialist softwareSpecialist){
        softwareSpecialists.put(softwareSpecialist.id, softwareSpecialist);
    }

    public SoftwareSpecialist get(int id){
        return softwareSpecialists.get(id);
    }

    public void remove(int id){
        softwareSpecialists.remove(id);
    }

    public void update(SoftwareSpecialist softwareSpecialist){
        SoftwareSpecialist x = softwareSpecialists.get(softwareSpecialist.id);
        x.userName = softwareSpecialist.userName;
        x.password = softwareSpecialist.password;
    }

    public List<SoftwareSpecialist> list(){
        return new ArrayList<>(softwareSpecialists.values());
    }
}