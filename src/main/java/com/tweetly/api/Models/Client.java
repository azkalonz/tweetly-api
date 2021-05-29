package com.tweetly.api.Models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Clients")
public class Client extends User {
    @Id
    private String id;
    private List<Client> followers = new ArrayList<Client>();
    private List<Client> following = new ArrayList<Client>();

    public String getId() {
        return this.id;
    }

    public void addFollower(Client follower) {
        followers.add(follower);
    }

    public Client withoutFollowers() {
        Client c = new Client();
        c.setId(id);
        c.setFirstName(this.getFirstName());
        c.setLastName(this.getLastName());
        c.setEmail(this.getEmail());
        return c;
    }

    public void removeFollower(String id) {
        List<Client> toRemove = new ArrayList<Client>();
        for (Client c : followers) {
            if (c.id.equals(id)) {
                toRemove.add(c);
            }
        }
        followers.removeAll(toRemove);
    }

    public void removeFollowing(String id) {
        List<Client> toRemove = new ArrayList<Client>();
        for (Client c : following) {
            if (c.id.equals(id)) {
                toRemove.add(c);
            }
        }
        following.removeAll(toRemove);

    }

    public void addFollowing(Client following) {
        this.following.add(following);
    }

    public Client getFollowingById(String id) {
        Client client = new Client();
        for (Client c : following) {
            if (c.id == id) {
                client = c;
            }
        }
        return client;
    }

    public void unFollow(Client follower) {
        followers.remove(follower);
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Client> getFollowers() {
        return this.followers;
    }

    public void setFollowers(List<Client> followers) {
        this.followers = followers;
    }

    public List<Client> getFollowing() {
        return this.following;
    }

    public void setFollowing(ArrayList<Client> following) {
        this.following = following;
    }

}
