package com.tweetly.api.Controllers;

import java.util.List;
import java.util.Optional;

import com.tweetly.api.Models.Client;
import com.tweetly.api.Models.Comment;
import com.tweetly.api.Models.Post;
import com.tweetly.api.Repositories.ClientsRepository;
import com.tweetly.api.Repositories.PostsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {
    @Autowired
    private PostsRepository postsRepo;
    private ClientsRepository clientsRepo;

    PostController(PostsRepository a, ClientsRepository b) {
        postsRepo = a;
        clientsRepo = b;
    }

    @PostMapping("/add-comment")
    public Comment addComment(@RequestParam("client_id") String clientId, @RequestParam("post_id") String postId,
            @RequestParam(name = "message", defaultValue = "") String message) {
        Comment comment = new Comment();
        Optional<Client> client = clientsRepo.findById(clientId);
        Optional<Post> post = postsRepo.findById(postId);
        if (client.isEmpty() || post.isEmpty()) {
            return null;
        }
        comment.setAuthor(client.get());
        comment.setMessage(message);
        Post thePost = post.get();
        Comment theComment = thePost.addComment(comment);
        postsRepo.save(thePost);
        return theComment;
    }

    @DeleteMapping("/delete-comment/{comment_id}")
    public String deleteComment(@RequestParam("post_id") String postId, @PathVariable("comment_id") int commentId) {
        Optional<Post> post = postsRepo.findById(postId);
        if (post.isEmpty()) {
            return "Post not found";
        } else {
            Post thePost = post.get();
            thePost.getComments().remove(thePost.findCommentById(commentId));
            postsRepo.save(thePost);
            return "Success";
        }
    }

    @PostMapping("/add-post")
    public Post addPost(@RequestParam(name = "message", defaultValue = "", required = false) String message,
            @RequestParam("client_id") String clientId, @RequestParam(required = true, name = "date") String date) {
        Optional<Client> client = clientsRepo.findById(clientId);
        if (client.isEmpty()) {
            return null;
        }
        Post post = new Post();
        post.setMessage(message);
        post.setAuthor(client.get());
        post.setDate(date);
        postsRepo.insert(post);
        return post;
    }

    @DeleteMapping("/delete-post/{post_id}")
    public String deletePost(@PathVariable("post_id") String postId) {
        Optional<Post> post = postsRepo.findById(postId);
        if (post.isEmpty()) {
            return "Post not found";
        } else {
            postsRepo.delete(post.get());
            return "Success";
        }

    }

    @GetMapping("/posts")
    public List<Post> posts() {
        return postsRepo.findAll();
    }

}
