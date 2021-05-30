package com.tweetly.api.Controllers;

import java.util.List;
import java.util.Optional;

import com.tweetly.api.Models.Client;
import com.tweetly.api.Repositories.ClientsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:8081")
@RestController
public class AuthController {
    @Autowired
    ClientsRepository repository;

    AuthController(ClientsRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/register")
    public Client register(@RequestBody Client client) {
        repository.insert(client);
        return client;
    }

    @PostMapping("/login")
    public Client test(@RequestParam("email") String email, @RequestParam("password") String password) {
        return repository.findUser(email, password);
    }

    @GetMapping("/user/{id}")
    public Client getUser(@PathVariable("id") String id) {
        Optional<Client> client = repository.findById(id);
        if (client.isPresent()) {
            return client.get();
        } else {
            return null;
        }
    }

    @PostMapping("/update-user")
    public Client updateUser(@RequestBody Client client) {
        Client user = getUser(client.getId());
        user.setFirstName(client.getFirstName());
        user.setLastName(client.getLastName());
        user.setEmail(client.getEmail());
        user.setPassword(client.getPassword());

        return repository.save(user);
    }

    @GetMapping("/users")
    public List<Client> users() {
        return repository.findAll();
    }

    @PostMapping("/ban/{client_id}")
    public Client banUser(@PathVariable("client_id") String id) {
        Optional<Client> client = repository.findById(id);
        if (client.isPresent()) {
            Client theClient = client.get();
            theClient.setBanned(true);
            return repository.save(theClient);
        } else {
            return null;
        }
    }

    @PostMapping("/unban/{client_id}")
    public Client unbanUser(@PathVariable("client_id") String id) {
        Optional<Client> client = repository.findById(id);
        if (client.isPresent()) {
            Client theClient = client.get();
            theClient.setBanned(false);
            return repository.save(theClient);
        } else {
            return null;
        }
    }
}
