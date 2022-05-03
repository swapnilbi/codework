package com.codework.service.impl;

import com.codework.entity.ChallengeInstance;
import com.codework.entity.ChallengeInstanceSubmission;
import com.codework.entity.ChallengeSubscription;
import com.codework.entity.Problem;
import com.codework.enums.ChallengeInstanceStatus;
import com.codework.enums.ChallengeSubscriptionStatus;
import com.codework.enums.SubmissionStatus;
import com.codework.exception.BusinessException;
import com.codework.exception.SystemException;
import com.codework.model.ChallengeSubmitInput;
import com.codework.model.ProblemSolutionInput;
import com.codework.repository.ChallengeInstanceRepository;
import com.codework.repository.ChallengeInstanceSubmissionRepository;
import com.codework.repository.ChallengeSubscriptionRepository;
import com.codework.repository.SequenceGenerator;
import com.codework.service.IChallengeInstanceService;
import com.codework.service.IChallengeSubscriptionService;
import com.codework.service.IProblemSolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChallengeInstanceService implements IChallengeInstanceService {

	@Autowired
	IProblemSolutionService problemSolutionService;

	@Autowired
	ChallengeInstanceRepository challengeInstanceRepository;

	@Autowired
	ChallengeInstanceSubmissionRepository challengeInstanceSubmissionRepository;

	@Autowired
	SequenceGenerator sequenceGenerator;

	@Override
	public ChallengeInstanceSubmission startChallenge(Long challengeInstanceId, Long userId) throws BusinessException {
		ChallengeInstance challengeInstance = challengeInstanceRepository.findById(challengeInstanceId).get();
		validateChallengeStatus(challengeInstance);
		ChallengeInstanceSubmission challengeInstanceSubmission = null;
		Optional<ChallengeInstanceSubmission> existingSubmission = challengeInstanceSubmissionRepository.findByChallengeInstanceIdAndUserId(challengeInstanceId,userId);
		if(existingSubmission.isPresent()){
			challengeInstanceSubmission = existingSubmission.get();
			if(challengeInstanceSubmission.getSubmissionStatus().equals(SubmissionStatus.SUBMITTED)){
				throw new BusinessException("You have already submitted this challenge");
			}
		}else{
			challengeInstanceSubmission = new ChallengeInstanceSubmission();
			challengeInstanceSubmission.setId(sequenceGenerator.generateSequence(ChallengeInstanceSubmission.SEQUENCE_NAME));
			challengeInstanceSubmission.setUserId(userId);
			challengeInstanceSubmission.setChallengeId(challengeInstance.getChallengeId());
			challengeInstanceSubmission.setChallengeInstanceId(challengeInstanceId);
			challengeInstanceSubmission.setSubmissionStatus(SubmissionStatus.IN_PROGRESS);
			challengeInstanceSubmission.setStartTime(new Date());
		}
		challengeInstanceSubmissionRepository.save(challengeInstanceSubmission);
		return challengeInstanceSubmission;
	}

	private void validateChallengeStatus(ChallengeInstance challengeInstance) throws BusinessException {
		if(challengeInstance.getInstanceStatus() == ChallengeInstanceStatus.EXPIRED){
			throw new BusinessException("Sorry! This Challenge is expired");
		}
	}

	@Override
	public ChallengeInstanceSubmission submitChallenge(ChallengeSubmitInput submitInput, Long userId) throws SystemException, BusinessException {
		ChallengeInstance challengeInstance = getChallengeInstance(submitInput.getChallengeInstanceId());
		validateChallengeStatus(challengeInstance);
		ChallengeInstanceSubmission challengeInstanceSubmission = getChallengeInstanceSubmission(submitInput.getChallengeInstanceId(),userId).get();
		if(challengeInstanceSubmission.getSubmissionStatus().equals(SubmissionStatus.SUBMITTED)){
			throw new BusinessException("You have already submitted this challenge");
		}
		if(submitInput.getSolutionList()!=null){
			for(ProblemSolutionInput solutionInput : submitInput.getSolutionList()){
				solutionInput.setSubmitted(true);
				problemSolutionService.saveSolution(solutionInput,userId);
			}
		}
		challengeInstanceSubmission.setSubmissionTime(new Date());
		challengeInstanceSubmission.setSubmissionStatus(SubmissionStatus.SUBMITTED);
		challengeInstanceSubmissionRepository.save(challengeInstanceSubmission);
		return challengeInstanceSubmission;
	}

	@Override
	public Optional<ChallengeInstanceSubmission> getChallengeInstanceSubmission(Long challengeInstanceId, Long userId) {
		return challengeInstanceSubmissionRepository.findByChallengeInstanceIdAndUserId(challengeInstanceId, userId);
	}

	@Override
	public ChallengeInstanceSubmission saveChallengeInstanceSubmission(ChallengeInstanceSubmission challengeInstanceSubmission) {
		return challengeInstanceSubmissionRepository.save(challengeInstanceSubmission);
	}

	@Override
	public List<ChallengeInstance> getChallengeInstanceList(Long challengeId) {
		return challengeInstanceRepository.findByChallengeId(challengeId);
	}

	@Override
	public ChallengeInstance getChallengeInstance(Long id) {
		return challengeInstanceRepository.findById(id).get();
	}

	@Override
	public ChallengeInstance createChallengeInstance(ChallengeInstance challengeInstance) {
		challengeInstance.setId(sequenceGenerator.generateSequence(ChallengeInstance.SEQUENCE_NAME));
		return challengeInstanceRepository.save(challengeInstance);
	}

	@Override
	public ChallengeInstance updateChallengeInstance(ChallengeInstance challengeInstance) {
		return challengeInstanceRepository.save(challengeInstance);
	}

	@Override
	public ChallengeInstance startChallengeInstance(Long instanceId) {
		ChallengeInstance challengeInstance = getChallengeInstance(instanceId);
		challengeInstance.setInstanceStatus(ChallengeInstanceStatus.LIVE);
		return challengeInstanceRepository.save(challengeInstance);
	}

}
