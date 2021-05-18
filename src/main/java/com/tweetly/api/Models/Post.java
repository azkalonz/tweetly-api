package com.tweetly.api.Models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Posts")
public class Post {
    @Id
    private String id;
    private String message;
    private Client author;
    private int likes;
    private String date;
    private List<Comment> comments = new ArrayList<Comment>();

    public Comment findCommentById(int commentId) {
        Comment comment = new Comment();
        for (Comment c : comments) {
            if (c.getId() == commentId) {
                comment = c;
            }
        }
        return comment;
    }

    public Comment addComment(Comment comment) {
        comment.setId(comments.size() + 1);
        comments.add(comment);
        return comment;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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
