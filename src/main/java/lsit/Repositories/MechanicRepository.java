package lsit.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import lsit.Models.Mechanic;
import org.springframework.stereotype.Repository;

@Repository
public class MechanicRepository {
    static HashMap<Integer, Mechanic> mechanics = new HashMap<Integer, Mechanic>();

    public void add(Mechanic mechanic){
        mechanics.put(mechanic.id, mechanic);
    }

    public Mechanic get(int id){
        return mechanics.get(id);
    }

    public void remove(int id){
        mechanics.remove(id);
    }

    public void update(Mechanic mechanic){
        Mechanic x = mechanics.get(mechanic.id);
        x.userName = mechanic.userName;
        x.password = mechanic.password;
    }

    public List<Mechanic> list(){
        return new ArrayList<>(mechanics.values());
    }
}