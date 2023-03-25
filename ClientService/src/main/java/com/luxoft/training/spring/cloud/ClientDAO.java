package com.luxoft.training.spring.cloud;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientDAO {
    private final ClientRepository repo;

    public ClientDAO(ClientRepository repo) {
        this.repo = repo;
    }

    public List<ClientEntity> getInfo() {
        return repo.findAll();
    }

    public ClientEntity getInfo(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException(String.format("No client with provided id %s", id)));
    }

    public boolean removeById(Integer id) {
        repo.deleteById(id);
        return repo.findById(id).isEmpty();
    }

    public ClientEntity create(String name) {
        ClientEntity client = new ClientEntity();
        client.setName(name);
        return repo.save(client);
    }

    public boolean update(Integer id, String name) {
        ClientEntity client = repo.findById(id).orElse(null);
        if (client == null) {
            return false;
        } else {
            client.setName(name);
            repo.save(client);
            return true;
        }
    }
}
