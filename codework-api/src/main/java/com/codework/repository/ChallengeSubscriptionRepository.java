package com.codework.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.codework.entity.ChallengeSubscription;

public interface ChallengeSubscriptionRepository extends MongoRepository<ChallengeSubscription, Long>{
	Optional<ChallengeSubscription> findByChallengeIdAndUserId(long challengeId, Long userId);
}
