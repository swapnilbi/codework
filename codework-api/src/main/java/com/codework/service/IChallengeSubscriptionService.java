package com.codework.service;

import java.util.Optional;

import com.codework.entity.ChallengeSubscription;
import com.codework.enums.ChallengeSubscriptionStatus;

public interface IChallengeSubscriptionService {
	
    Optional<ChallengeSubscription> registerChallenge(long id, ChallengeSubscriptionStatus register);

}
