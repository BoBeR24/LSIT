package lsit.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lsit.Models.Client;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepository {
    static HashMap<Integer, Client> clients = new HashMap<Integer, Client>();

    public void add(Client client){
        clients.put(client.id, client);
    }

    public Client get(Integer id){
        return clients.get(id);
    }

    public void remove(Integer id){
        clients.remove(id);
    }

    public void update(Client client){
        Client x = clients.get(client.id);
        x.userName = client.userName;
        x.password = client.password;
    }

    public List<Client> list(){
        return new ArrayList<>(clients.values());
    }
}
