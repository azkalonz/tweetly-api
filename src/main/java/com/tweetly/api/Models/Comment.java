package com.tweetly.api.Models;

public class Comment {
    private int id;
    private String message;
    private Client author;
    private String date;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
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

}
