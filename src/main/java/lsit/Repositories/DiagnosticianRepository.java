package lsit.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lsit.Models.Diagnostician;
import org.springframework.stereotype.Repository;

@Repository
public class DiagnosticianRepository {
    static HashMap<Integer, Diagnostician> diagnosticians = new HashMap<Integer, Diagnostician>();

    public void add(Diagnostician diagnostician){
        diagnosticians.put(diagnostician.id, diagnostician);
    }

    public Diagnostician get(int id){
        return diagnosticians.get(id);
    }

    public void remove(int id){
        diagnosticians.remove(id);
    }

    public void update(Diagnostician diagnostician){
        Diagnostician x = diagnosticians.get(diagnostician.id);
        x.userName = diagnostician.userName;
        x.password = diagnostician.password;
    }

    public List<Diagnostician> list(){
        return new ArrayList<>(diagnosticians.values());
    }
}
