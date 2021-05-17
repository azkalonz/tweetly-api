package com.tweetly.api.Models;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Clients")
public class Client extends User {
    @Id
    private String id;
    private ArrayList<Client> followers;
    private ArrayList<Client> following;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Client> getFollowers() {
        return this.followers;
    }

    public void setFollowers(ArrayList<Client> followers) {
        this.followers = followers;
    }

    public ArrayList<Client> getFollowing() {
        return this.following;
    }

    public void setFollowing(ArrayList<Client> following) {
        this.following = following;
    }

}
