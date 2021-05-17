package com.tweetly.api.Controllers;

import java.util.List;
import java.util.Optional;

import com.tweetly.api.Models.Client;
import com.tweetly.api.Models.Post;
import com.tweetly.api.Repositories.ClientsRepository;
import com.tweetly.api.Repositories.PostsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/add-post")
    public Post addPost(@RequestParam(name = "message", defaultValue = "", required = false) String message,
            @RequestParam("client_id") String clientId) {
        Optional<Client> client = clientsRepo.findById(clientId);
        if (client.isEmpty()) {
            return null;
        }
        Post post = new Post();
        post.setMessage(message);
        post.setAuthor(client.get());
        postsRepo.insert(post);
        return post;
    }

    @GetMapping("/posts")
    public List<Post> posts() {
        return postsRepo.findAll();
    }

}
