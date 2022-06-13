package com.codework.service.impl;

import java.util.*;

import com.codework.entity.ChallengeInstance;
import com.codework.entity.ChallengeInstanceSubmission;
import com.codework.enums.ChallengeInstanceStatus;
import com.codework.enums.SubmissionStatus;
import com.codework.model.Leaderboard;
import com.codework.model.LiveChallengeDetails;
import com.codework.model.UserSubmission;
import com.codework.service.IChallengeInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codework.entity.Challenge;
import com.codework.entity.ChallengeSubscription;
import com.codework.enums.ChallengeStatus;
import com.codework.model.ChallengeDetails;
import com.codework.repository.ChallengeRepository;
import com.codework.repository.SequenceGenerator;
import com.codework.service.IChallengeService;
import com.codework.service.IChallengeSubscriptionService;

@Service
public class ChallengeService implements IChallengeService {

	@Autowired
	ChallengeRepository challengeRepository;

	@Autowired
	IChallengeInstanceService challengeInstanceService;

	@Autowired
	SequenceGenerator sequenceGenerator;
	
	@Autowired
	IChallengeSubscriptionService challengeSubscriptionService;

	@Override
	public Optional<ChallengeDetails> getChallengeDetails(Long challengeId, Long userId) {
		 Optional<Challenge> challenge = challengeRepository.findById(challengeId);
		 if(challenge.isPresent()){
			 Optional<ChallengeSubscription> challengeSubscription = challengeSubscriptionService.getChallengeSubscription(challengeId,userId);
			 ChallengeDetails challengeDetails = new ChallengeDetails(challenge.get());
			 if(challengeSubscription.isPresent()) {
				 challengeDetails.setChallengeSubscription(challengeSubscription.get());
			 }
			 List<ChallengeInstance> challengeInstances = challengeInstanceService.getChallengeInstanceList(challengeId);
			 if(challengeInstances!=null && !challengeInstances.isEmpty()){
				 List<UserSubmission> userSubmissionList = new ArrayList<>();
				 for(ChallengeInstance challengeInstance : challengeInstances){
					 UserSubmission userSubmission = new UserSubmission();
					 userSubmission.setProblem(challengeInstance.getName());
					 userSubmission.setChallengeInstanceId(challengeInstance.getId());
					 userSubmission.setStartTime(challengeInstance.getStartDate());
					 userSubmission.setEndTime(challengeInstance.getEndDate());
					 userSubmission.setProblemType(challengeInstance.getType());
					 Optional<ChallengeInstanceSubmission> existingInstanceSubmission = challengeInstanceService.getChallengeInstanceSubmission(challengeInstance.getId(),userId);
					 if(existingInstanceSubmission.isPresent()){
						 ChallengeInstanceSubmission challengeInstanceSubmission = existingInstanceSubmission.get();
						 userSubmission.setSubmissionStatus(challengeInstanceSubmission.getSubmissionStatus());
						 userSubmission.setSubmittedTime(challengeInstanceSubmission.getSubmissionTime());
					 }else if(challengeInstance.getInstanceStatus().equals(ChallengeInstanceStatus.EXPIRED)){
						 userSubmission.setSubmissionStatus(SubmissionStatus.EXPIRED);
					 }else if(challengeInstance.getInstanceStatus().equals(ChallengeInstanceStatus.LIVE)){
						 userSubmission.setSubmissionStatus(SubmissionStatus.LIVE);
					 }
					 userSubmissionList.add(userSubmission);
				 }
				 challengeDetails.setUserSubmissions(userSubmissionList);
			 }
			 return Optional.of(challengeDetails);
		 }
		 return Optional.empty();
	}

	@Override
	public Optional<ChallengeDetails> getChallenge(Long id, Long userId) {
		Optional<Challenge> challenge = challengeRepository.findById(id);
		if(challenge.isPresent()){
			Optional<ChallengeSubscription> challengeSubscription = challengeSubscriptionService.getChallengeSubscription(id,userId);
			ChallengeDetails challengeDetails = new ChallengeDetails(challenge.get());
			if(challengeSubscription.isPresent()) {
				challengeDetails.setChallengeSubscription(challengeSubscription.get());
			}
			return Optional.of(challengeDetails);
		}
		return Optional.empty();
	}

	@Override
	public List<ChallengeDetails> getChallenges(Long userId) {
		List<ChallengeDetails> challengeDetailsList = new ArrayList<>();
		List<Challenge> challengeList = challengeRepository.findByStatusIn(Arrays.asList(ChallengeStatus.LIVE));
		if(challengeList!= null && challengeList.size() > 0){
			for(Challenge challenge : challengeList){
				challengeDetailsList.add(getChallenge(challenge.getId(), userId).get());
			}
		}
		return challengeDetailsList;
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

	@Override
	public LiveChallengeDetails getLiveChallengeDetails(Long challengeInstanceId, Long userId) {
		ChallengeInstance challengeInstance = challengeInstanceService.getChallengeInstance(challengeInstanceId);
		ChallengeDetails challengeDetails = getChallengeDetails(challengeInstance.getChallengeId(),userId).get();
		Optional<ChallengeInstanceSubmission> challengeInstanceSubmission = challengeInstanceService.getChallengeInstanceSubmission(challengeInstanceId,userId);
		ChallengeInstanceSubmission instanceSubmission = challengeInstanceSubmission.orElse(null);
		return new LiveChallengeDetails(challengeDetails,challengeInstance, instanceSubmission);
	}

	@Override
	public Challenge startChallenge(Long challengeId) {
		Challenge challenge = challengeRepository.findById(challengeId).get();
		challenge.setStatus(ChallengeStatus.LIVE);
		challengeRepository.save(challenge);
		return challenge;
	}

	@Override
	public Challenge stopChallenge(Long challengeId) {
		Challenge challenge = challengeRepository.findById(challengeId).get();
		challenge.setStatus(ChallengeStatus.SCHEDULED);
		challengeRepository.save(challenge);
		return challenge;
	}

	@Override
	public Challenge updateChallenge(ChallengeDetails challengeDetails) {
		Challenge challenge = challengeRepository.findById(challengeDetails.getId()).get();
		challenge.setShortDescription(challengeDetails.getShortDescription());
		challenge.setName(challengeDetails.getName());
		challenge.setBannerImage(challengeDetails.getBannerImage());
		challenge.setLongDescription(challengeDetails.getLongDescription());
		challenge.setStartDate(challengeDetails.getStartDate());
		challenge.setEndDate(challengeDetails.getEndDate());
		challengeRepository.save(challenge);
		return challenge;
	}

	@Override
	public void deleteChallenge(Long challengeId) {
		List<ChallengeInstance> challengeInstanceList = challengeInstanceService.getChallengeInstanceList(challengeId);
		if(challengeInstanceList!=null && !challengeInstanceList.isEmpty()){
			challengeInstanceList.stream().forEach(t -> challengeInstanceService.deleteChallengeInstance(t.getId()));
		}
		challengeRepository.deleteById(challengeId);
	}

}
