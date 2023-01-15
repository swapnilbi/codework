package com.codework.repository;

import com.codework.entity.ChallengeSubscription;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChallengeSubscriptionRepository extends MongoRepository<ChallengeSubscription, Long>{
	Optional<ChallengeSubscription> findByChallengeIdAndUserId(long challengeId, Long userId);

    List<ChallengeSubscription> findByChallengeId(Long challengeId);
}
