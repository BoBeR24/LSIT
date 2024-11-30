package lsit.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lsit.Models.Assembler;
import org.springframework.stereotype.Repository;

@Repository
public class AssemblerRepository {
    static HashMap<Integer, Assembler> assemblers = new HashMap<Integer, Assembler>();

    public void add(Assembler assembler){
        assemblers.put(assembler.id, assembler);
    }

    public Assembler get(Integer id){
        return assemblers.get(id);
    }

    public void remove(Integer id){
        assemblers.remove(id);
    }

    public void update(Assembler assembler){
        Assembler x = assemblers.get(assembler.id);
        x.userName = assembler.userName;
        x.password = assembler.password;
    }

    public List<Assembler> list(){
        return new ArrayList<>(assemblers.values());
    }
}