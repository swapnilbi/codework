package com.codework.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codework.entity.Challenge;
import com.codework.entity.ChallengeStatus;
import com.codework.model.ChallengeDetails;
import com.codework.repository.ChallengeRepository;
import com.codework.repository.SequenceGenerator;
import com.codework.service.IChallengeService;

@Service
public class ChallengeService implements IChallengeService {

	@Autowired
	ChallengeRepository challengeRepository;

	@Autowired
	SequenceGenerator sequenceGenerator;

	@Override
	public Optional<ChallengeDetails> getChallenge(long id) {
		 Optional<Challenge> challenge = challengeRepository.findById(id);
		 if(challenge.isPresent()){
		 	return Optional.of(new ChallengeDetails(challenge.get()));
		 }
		 return Optional.empty();
	}

	@Override
	public List<ChallengeDetails> getChallenges() {
		List<ChallengeDetails> challengeDetailsList = new ArrayList<>();
		List<Challenge> challengeList = challengeRepository.findAll();
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
		return Optional.of(new ChallengeDetails(challengeRepository.save(challenge)));
	}

}
