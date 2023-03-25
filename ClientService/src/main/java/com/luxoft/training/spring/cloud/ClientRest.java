package com.luxoft.training.spring.cloud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientRest {

    private final ClientDAO clientDAO;

    public ClientRest(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @GetMapping("/get")
    public List<ClientEntity> getAllClients() {
        return clientDAO.getInfo();
    }

    @GetMapping("/create")
    public ClientEntity createClient(@RequestParam(name = "name") String name) {
        return clientDAO.create(name);
    }


    @GetMapping("/update/{id}")
    public boolean updateClient(@RequestParam(name = "name") String name, @PathVariable(name = "id") Integer id) {
        return clientDAO.update(id, name);
    }

    @GetMapping("/get/{id}")
    public ClientEntity getById(@PathVariable(name = "id") Integer id) {
        return clientDAO.getInfo(id);
    }

    @GetMapping("/delete/{id}")
    public boolean deleteClient(@PathVariable(name = "id") Integer id) {
        return clientDAO.removeById(id);
    }
}
