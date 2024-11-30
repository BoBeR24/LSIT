package lsit.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import lsit.Models.Electrician;
import org.springframework.stereotype.Repository;

@Repository
public class ElectricianRepository {
    static HashMap<Integer, Electrician> electricians = new HashMap<Integer, Electrician>();

    public void add(Electrician electrician){
        electricians.put(electrician.id, electrician);
    }

    public Electrician get(int id){
        return electricians.get(id);
    }

    public void remove(int id){
        electricians.remove(id);
    }

    public void update(Electrician electrician){
        Electrician x = electricians.get(electrician.id);
        x.userName = electrician.userName;
        x.password = electrician.password;
    }

    public List<Electrician> list(){
        return new ArrayList<>(electricians.values());
    }
}