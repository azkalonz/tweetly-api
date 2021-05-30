package com.tweetly.api.Repositories;

import com.tweetly.api.Models.Report;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportsRepository extends MongoRepository<Report, String> {

}
