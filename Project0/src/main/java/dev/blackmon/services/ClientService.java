package dev.blackmon.services;

import dev.blackmon.models.Client;
import dev.blackmon.repositories.ClientDAO;

import java.util.List;

public class ClientService {
    private ClientDAO cd = new ClientDAO();

    public Client createClient(Client c) {
        return cd.createClient(c);
    }

    public List<Client> getAllClients() {
        return cd.getAllClients();
    }

    public Client getClientById(int id) {
        return cd.getClientById(id);
    }

    public Client updateClient(Client updatedClient) {
        return cd.updateClient(updatedClient);
    }

    public boolean deleteClient(int id) {
        return cd.deleteClient(id);
    }

}
