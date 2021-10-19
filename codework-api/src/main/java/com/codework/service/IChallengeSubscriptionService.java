package com.codework.service;

import java.util.Optional;

import com.codework.entity.ChallengeSubscription;
import com.codework.enums.ChallengeSubscriptionStatus;
import com.codework.exception.BusinessException;
import com.codework.model.ChallengeDetails;

public interface IChallengeSubscriptionService {
	
    Optional<ChallengeSubscription> registerChallenge(Long id) throws BusinessException;
    
    Optional<ChallengeSubscription> getChallengeSubscription(Long challengeId, String userId);

	Optional<ChallengeSubscription> startChallenge(Long challengeId);
	
	Optional<ChallengeSubscription> submitChallenge(Long challengeId);

}
