package com.tweetly.api.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.tweetly.api.Models.Client;
import com.tweetly.api.Models.Comment;
import com.tweetly.api.Models.Post;
import com.tweetly.api.Models.Report;
import com.tweetly.api.Repositories.ClientsRepository;
import com.tweetly.api.Repositories.PostsRepository;
import com.tweetly.api.Repositories.ReportsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {
    @Autowired
    private PostsRepository postsRepo;
    private ClientsRepository clientsRepo;
    private ReportsRepository reportsRepo;

    PostController(PostsRepository a, ClientsRepository b, ReportsRepository c) {
        postsRepo = a;
        clientsRepo = b;
        reportsRepo = c;
    }

    @PostMapping("/add-comment")
    public Comment addComment(@RequestBody Map<String, Object> payload) {
        String clientId = payload.get("client_id").toString();
        String postId = payload.get("post_id").toString();
        String message = payload.get("message").toString();
        String date = payload.get("date").toString();
        Comment comment = new Comment();
        Optional<Client> client = clientsRepo.findById(clientId);
        Optional<Post> post = postsRepo.findById(postId);
        if (client.isEmpty() || post.isEmpty()) {
            return null;
        }
        comment.setDate(date);
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
    public Post addPost(@RequestBody Map<String, Object> payload) {
        String clientId = payload.get("client_id").toString();
        String message = payload.get("message").toString();
        String date = payload.get("date").toString();
        Optional<Client> client = clientsRepo.findById(clientId);
        if (client.isEmpty()) {
            return null;
        }
        Post post = new Post();
        post.setMessage(message);
        post.setAuthor(client.get().withoutFollowers());
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

    @PostMapping("/like-post/{post_id}")
    public String likePost(@PathVariable("post_id") String postId) {
        Optional<Post> post = postsRepo.findById(postId);
        if (post.isEmpty()) {
            return "Post not found";
        } else {
            Post thePost = post.get();
            thePost.setLikes(thePost.getLikes() + 1);
            postsRepo.save(thePost);
            return "Success";
        }
    }

    @PostMapping("/unfollow/{client_id}/{follower_id}")
    public Client unfollowUser(@PathVariable("client_id") String clientId,
            @PathVariable("follower_id") String followerId) {
        Optional<Client> client = clientsRepo.findById(clientId);
        Optional<Client> follower = clientsRepo.findById(followerId);

        if (client.isPresent() && follower.isPresent()) {
            Client theClient = client.get();
            Client theFollower = follower.get();
            theFollower.removeFollowing(theClient.getId());
            theClient.removeFollower(theFollower.getId());
            List<Client> clients = new ArrayList<Client>();
            clients.add(theClient);
            clients.add(theFollower);
            clientsRepo.saveAll(clients);
            return theClient;
        } else {
            return null;
        }
    }

    @PostMapping("/follow/{client_id}/{follower_id}")
    public Client followUser(@PathVariable("client_id") String clientId,
            @PathVariable("follower_id") String followerId) {
        Optional<Client> client = clientsRepo.findById(clientId);
        Optional<Client> follower = clientsRepo.findById(followerId);

        if (client.isPresent() && follower.isPresent()) {
            Client theClient = client.get();
            Client theFollower = follower.get();
            theFollower.addFollowing(theClient.withoutFollowers());
            theClient.addFollower(theFollower.withoutFollowers());
            List<Client> clients = new ArrayList<Client>();
            clients.add(theClient);
            clients.add(theFollower);
            clientsRepo.saveAll(clients);
            return theClient;
        } else {
            return null;
        }
    }

    @PostMapping("/report-post/{post_id}/{client_id}")
    public String reportPost(@PathVariable("post_id") String postId, @PathVariable("client_id") String clientId) {
        Optional<Client> client = clientsRepo.findById(clientId);
        Optional<Post> post = postsRepo.findById(postId);

        if (client.isPresent() && post.isPresent()) {
            Client theClient = client.get();
            Post thePost = post.get();
            String date = java.time.LocalDateTime.now().toString();
            Report report = new Report();

            report.setReporter(theClient.withoutFollowers());
            report.setPost(thePost);
            report.setDate(date);

            reportsRepo.save(report);
            return "Success";
        } else {
            return "Client / Post not found";
        }
    }

    @GetMapping("/reports")
    public List<Report> reports() {
        return reportsRepo.findAll();
    }

    @PostMapping("/delete-report/{report_id}")
    public String deleteReport(@PathVariable("report_id") String id) {
        try {
            reportsRepo.deleteById(id);
            return "Success";
        } catch (Exception e) {
            return "Error";
        }
    }
}
