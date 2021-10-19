package com.codework.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codework.entity.ChallengeSubscription;
import com.codework.enums.ChallengeSubscriptionStatus;
import com.codework.exception.BusinessException;
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
	public Optional<ChallengeSubscription> registerChallenge(Long challengeId) throws BusinessException {
		Optional<ChallengeSubscription> existingSubscription = getChallengeSubscription(challengeId, "1");
		if(existingSubscription.isPresent()) {
			throw new BusinessException("You have been already registered");
		}
		ChallengeSubscription challengeSubscription = new ChallengeSubscription();
		challengeSubscription.setId(sequenceGenerator.generateSequence(ChallengeSubscription.SEQUENCE_NAME));
		challengeSubscription.setChallengeId(challengeId);
		challengeSubscription.setStatus(ChallengeSubscriptionStatus.REGISTERED);
		challengeSubscription.setUserId("1");
		return Optional.of(challengeSubscriptionRepository.save(challengeSubscription));
	}

	@Override
	public Optional<ChallengeSubscription> getChallengeSubscription(Long challengeId, String userId) {
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