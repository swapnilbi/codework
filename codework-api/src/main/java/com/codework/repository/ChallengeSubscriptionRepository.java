package com.codework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.codework.entity.Challenge;
import com.codework.entity.ChallengeSubscription;

public interface ChallengeSubscriptionRepository extends MongoRepository<ChallengeSubscription, Long>{

}
