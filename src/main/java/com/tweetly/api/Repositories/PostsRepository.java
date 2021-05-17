package com.tweetly.api.Repositories;

import com.tweetly.api.Models.Post;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostsRepository extends MongoRepository<Post, String> {

}
