package com.codework.service;

import java.util.List;
import java.util.Optional;

import com.codework.entity.ChallengeSubscription;
import com.codework.enums.ChallengeSubscriptionStatus;
import com.codework.exception.BusinessException;
import com.codework.exception.SystemException;
import com.codework.model.ChallengeDetails;
import com.codework.model.ChallengeSubmitInput;

public interface IChallengeSubscriptionService {
	
    Optional<ChallengeSubscription> registerChallenge(Long id, Long userId) throws BusinessException;
    
    Optional<ChallengeSubscription> getChallengeSubscription(Long challengeId, Long userId);
    List<ChallengeSubscription> getChallengeSubscription(Long challengeId);
}
