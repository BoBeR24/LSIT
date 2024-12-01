package lsit.Repositories;

import lsit.Models.Admin;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class AdminRepository {
    static HashMap<Integer, Admin> admins = new HashMap<Integer, Admin>();

    public void add(Admin admin){
        admins.put(admin.id, admin);
    }

    public Admin get(Integer id){
        return admins.get(id);
    }

    public void remove(Integer id){
        admins.remove(id);
    }

    public void update(Admin admin){
        Admin x = admins.get(admin.id);
        x.userName = admin.userName;
        x.password = admin.password;
    }

    public List<Admin> list(){
        return new ArrayList<>(admins.values());
    }

}
