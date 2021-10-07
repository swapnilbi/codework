package com.example.codework.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.codework.entity.Challenge;

public interface ChallengeRepository extends MongoRepository<Challenge, Integer>{

}
