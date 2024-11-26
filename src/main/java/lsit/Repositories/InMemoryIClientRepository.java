package lsit.Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import lsit.Models.Client;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryIClientRepository implements IClientRepository {
    static HashMap<UUID, Client> clients = new HashMap<UUID, Client>();

    public void add(Client client){
        clients.put(client.id, client);
    }

    public Client get(UUID id){
        return clients.get(id);
    }

    public void remove(UUID id){
        clients.remove(id);
    }

    public void update(Client client){
        Client x = clients.get(client.id);
        x.userName = client.userName;
        x.password = client.password;
        x.pendingRequestId = client.pendingRequestId;
    }

    public List<Client> list(){
        return new ArrayList<>(clients.values());
    }
}
