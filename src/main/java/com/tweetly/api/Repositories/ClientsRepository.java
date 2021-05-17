package com.tweetly.api.Repositories;

import com.tweetly.api.Models.Client;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ClientsRepository extends MongoRepository<Client, String> {
    @Query("{ 'email' : ?0, 'password': ?1 }")
    Client findUser(String email, String password);
}