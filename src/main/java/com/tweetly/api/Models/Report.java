package com.tweetly.api.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Reports")
public class Report {
    @Id
    String id;
    Client reporter;
    String date;
    Post post;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Client getReporter() {
        return this.reporter;
    }

    public void setReporter(Client reporter) {
        this.reporter = reporter;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Post getPost() {
        return this.post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
