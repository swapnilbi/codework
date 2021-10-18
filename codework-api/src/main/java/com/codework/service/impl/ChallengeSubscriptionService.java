package com.codework.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codework.entity.ChallengeSubscription;
import com.codework.enums.ChallengeSubscriptionStatus;
import com.codework.repository.ChallengeSubscriptionRepository;
import com.codework.repository.SequenceGenerator;
import com.codework.service.IChallengeSubscriptionService;

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

	@Override
	public Optional<ChallengeSubscription> getChallengeSubscription(long challengeId, String userId) {
		return challengeSubscriptionRepository.findByChallengeIdAndUserId(challengeId, userId);
	}

	@Override
	public Optional<ChallengeSubscription> startChallenge(Long challengeId) {
		ChallengeSubscription challengeSubscription = challengeSubscriptionRepository.findByChallengeIdAndUserId(challengeId, "1").get();
		challengeSubscription.setStatus(ChallengeSubscriptionStatus.STARTED);
		challengeSubscription.setStartDate(new Date());
		challengeSubscriptionRepository.save(challengeSubscription);
		return Optional.of(challengeSubscription);
	}

	@Override
	public Optional<ChallengeSubscription> submitChallenge(Long challengeId) {
		ChallengeSubscription challengeSubscription = challengeSubscriptionRepository.findByChallengeIdAndUserId(challengeId, "1").get();
		challengeSubscription.setStatus(ChallengeSubscriptionStatus.SUBMITTED);
		challengeSubscription.setEndDate(new Date());
		challengeSubscriptionRepository.save(challengeSubscription);
		return Optional.of(challengeSubscription);
	}
	
}
