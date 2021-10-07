package com.codework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.codework.entity.Challenge;

public interface ChallengeRepository extends MongoRepository<Challenge, Integer>{

}
