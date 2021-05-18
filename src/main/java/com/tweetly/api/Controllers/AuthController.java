package com.tweetly.api.Controllers;

import com.tweetly.api.Models.Client;
import com.tweetly.api.Repositories.ClientsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
}
