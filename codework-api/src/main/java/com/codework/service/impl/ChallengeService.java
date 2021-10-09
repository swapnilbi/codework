package com.codework.service.impl;

import com.codework.entity.Challenge;
import com.codework.entity.ChallengeStatus;
import com.codework.entity.ChallengeSubscription;
import com.codework.entity.SUBSCRIPTION_STATUS;
import com.codework.model.ChallengeDetails;
import com.codework.repository.ChallengeRepository;
import com.codework.repository.SequenceGenerator;
import com.codework.service.IChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class ChallengeService implements IChallengeService {

	@Autowired
	ChallengeRepository challengeRepository;

	@Autowired
	ChallengeSubscriptionRepository challengeSubscriptionRepository;

	@Autowired
	SequenceGenerator sequenceGenerator;

	@Override
	public Optional<ChallengeDetails> getChallenge(long id) {
		 Optional<Challenge> challenge = repository.findById(id);
		 if(challenge.isPresent()){
		 	return Optional.of(new ChallengeDetails(challenge.get()));
		 }
		 return Optional.empty();
	}

	@Override
	public List<ChallengeDetails> getChallenges() {
		List<ChallengeDetails> challengeDetailsList = new ArrayList<>();
		List<Challenge> challengeList = repository.findAll();
		if(challengeList!= null && challengeList.size() > 0){
			for(Challenge challenge : challengeList){
				challengeDetailsList.add(new ChallengeDetails(challenge));
			}
		}
		return challengeDetailsList;
	}

	@Override
	public Optional<ChallengeDetails> createChallenge(ChallengeDetails challengeInput) {
		Challenge challenge = new Challenge();
		challenge.setId(sequenceGenerator.generateSequence(Challenge.SEQUENCE_NAME));
		challenge.setStatus(ChallengeStatus.SCHEDULED);
		challenge.setShortDescription(challengeInput.getShortDescription());
		challenge.setName(challengeInput.getName());
		challenge.setBannerImage(challengeInput.getBannerImage());
		challenge.setLongDescription(challengeInput.getLongDescription());
		challenge.setStartDate(challengeInput.getStartDate());
		challenge.setEndDate(challengeInput.getEndDate());
		challenge.setCreatedAt(Calendar.getInstance().getTime());
		//challenge.setCreatedBy();
		return Optional.of(new ChallengeDetails(repository.save(challenge)));
	}

	@Override
	public Optional<ChallengeDetails> registerChallenge(long id, SUBSCRIPTION_STATUS register) {
		ChallengeSubscription challengeSubscription = new ChallengeSubscription();
		challengeSubscription.setSubId(sequenceGenerator.generateSequence(ChallengeSubscription.SEQUENCE_NAME));
		challengeSubscription.setChallengeId(id);
		challengeSubscription.setStatus(register);
		challengeSubscription.setUserId("1");
		return Optional.of(new ChallengeDetails(repository.save(challengeSubscription)));
	}

}
