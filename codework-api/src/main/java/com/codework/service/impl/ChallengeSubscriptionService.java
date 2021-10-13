package com.codework.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.codework.entity.ChallengeSubscription;
import com.codework.enums.ChallengeSubscriptionStatus;
import com.codework.repository.ChallengeSubscriptionRepository;
import com.codework.repository.SequenceGenerator;
import com.codework.service.IChallengeSubscriptionService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ChallengeSubscriptionService implements IChallengeSubscriptionService{

	@Autowired
	ChallengeSubscriptionRepository challengeSubscriptionRepository;
	@Autowired
	SequenceGenerator sequenceGenerator;
	
	@Override
	public Optional<ChallengeSubscription> registerChallenge(long id, ChallengeSubscriptionStatus register) {
		ChallengeSubscription challengeSubscription = new ChallengeSubscription();
		challengeSubscription.setSubId(sequenceGenerator.generateSequence(ChallengeSubscription.SEQUENCE_NAME));
		challengeSubscription.setChallengeId(id);
		challengeSubscription.setStatus(register);
		challengeSubscription.setUserId("1");
		return Optional.of(challengeSubscriptionRepository.save(challengeSubscription));
	}
}
