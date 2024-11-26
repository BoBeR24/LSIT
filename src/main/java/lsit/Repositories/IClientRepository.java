package lsit.Repositories;

import lsit.Models.Client;
import lsit.Models.Pet;

import java.util.List;
import java.util.UUID;

public interface IClientRepository {
    void add(Client p);

    Client get(UUID id);

    void remove(UUID id);

    void update(Client p);

    List<Client> list();
}
