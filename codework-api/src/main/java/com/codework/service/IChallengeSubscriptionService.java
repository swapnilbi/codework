package com.codework.service;

import java.util.Optional;

import com.codework.entity.ChallengeSubscription;
import com.codework.enums.ChallengeSubscriptionStatus;
import com.codework.exception.BusinessException;
import com.codework.exception.SystemException;
import com.codework.model.ChallengeDetails;
import com.codework.model.ChallengeSubmitInput;

public interface IChallengeSubscriptionService {
	
    Optional<ChallengeSubscription> registerChallenge(Long id) throws BusinessException;
    
    Optional<ChallengeSubscription> getChallengeSubscription(Long challengeId, String userId);

	ChallengeSubscription startChallenge(Long challengeId);

    ChallengeSubscription submitChallenge(ChallengeSubmitInput submitInput) throws SystemException, BusinessException;

}
