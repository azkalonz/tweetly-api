package com.tweetly.api.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Posts")
public class Post {
    @Id
    private String id;
    private String message;
    private Client author;
    private int likes;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Client getAuthor() {
        return this.author;
    }

    public void setAuthor(Client author) {
        this.author = author;
    }

    public int getLikes() {
        return this.likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

}
