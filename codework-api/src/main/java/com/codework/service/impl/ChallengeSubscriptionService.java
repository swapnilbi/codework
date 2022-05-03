package com.codework.service.impl;

import java.util.Date;
import java.util.Optional;

import com.codework.service.IProblemSolutionService;
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
	public Optional<ChallengeSubscription> registerChallenge(Long challengeId, Long userId) throws BusinessException {
		Optional<ChallengeSubscription> existingSubscription = getChallengeSubscription(challengeId, userId);
		if(existingSubscription.isPresent()) {
			throw new BusinessException("You have been already registered");
		}
		ChallengeSubscription challengeSubscription = new ChallengeSubscription();
		challengeSubscription.setId(sequenceGenerator.generateSequence(ChallengeSubscription.SEQUENCE_NAME));
		challengeSubscription.setChallengeId(challengeId);
		challengeSubscription.setStatus(ChallengeSubscriptionStatus.REGISTERED);
		challengeSubscription.setUserId(userId);
		challengeSubscription.setCreatedAt(new Date());
		return Optional.of(challengeSubscriptionRepository.save(challengeSubscription));
	}

	@Override
	public Optional<ChallengeSubscription> getChallengeSubscription(Long challengeId, Long userId) {
		return challengeSubscriptionRepository.findByChallengeIdAndUserId(challengeId, userId);
	}
	
}
