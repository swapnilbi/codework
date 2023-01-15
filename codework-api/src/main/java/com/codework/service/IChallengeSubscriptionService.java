package com.codework.service;

import com.codework.entity.ChallengeSubscription;
import com.codework.exception.BusinessException;

import java.util.List;
import java.util.Optional;

public interface IChallengeSubscriptionService {
	
    Optional<ChallengeSubscription> registerChallenge(Long id, Long userId) throws BusinessException;
    
    Optional<ChallengeSubscription> getChallengeSubscription(Long challengeId, Long userId);
    List<ChallengeSubscription> getChallengeSubscription(Long challengeId);
}
