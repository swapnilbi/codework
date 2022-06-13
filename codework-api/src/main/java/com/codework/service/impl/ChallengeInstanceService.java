package com.codework.service.impl;

import com.codework.entity.*;
import com.codework.enums.ChallengeInstanceStatus;
import com.codework.enums.EvaluationStatus;
import com.codework.enums.SolutionResult;
import com.codework.enums.SubmissionStatus;
import com.codework.exception.BusinessException;
import com.codework.exception.SystemException;
import com.codework.model.*;
import com.codework.repository.ChallengeInstanceRepository;
import com.codework.repository.ChallengeInstanceSubmissionRepository;
import com.codework.repository.SequenceGenerator;
import com.codework.service.*;
import com.codework.task.ProblemEvaluationTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChallengeInstanceService implements IChallengeInstanceService {

	@Autowired
	IProblemSolutionService problemSolutionService;

	@Autowired
	ChallengeInstanceRepository challengeInstanceRepository;

	@Autowired
	UserService userService;

	@Autowired
	ChallengeInstanceSubmissionRepository challengeInstanceSubmissionRepository;

	@Autowired
	IProblemEvaluationService problemEvaluationService;

	@Autowired
	IProblemService problemService;

	@Autowired
	ILanguageService languageService;

	@Autowired
	SequenceGenerator sequenceGenerator;

	@Autowired
	private TaskExecutor executor;

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
			throw new BusinessException("Sorry! You have already submitted this challenge");
		}
		if(submitInput.getSolutionList()!=null){
			for(ProblemSolutionInput solutionInput : submitInput.getSolutionList()){
				solutionInput.setSubmitted(true);
				solutionInput.setChallengeInstanceId(submitInput.getChallengeInstanceId());
				solutionInput.setChallengeInstanceSubmissionId(submitInput.getChallengeInstanceSubmissionId());
				problemSolutionService.saveSolution(solutionInput,userId);
			}
		}
		challengeInstanceSubmission.setSubmissionTime(new Date());
		challengeInstanceSubmission.setSubmissionStatus(SubmissionStatus.SUBMITTED);
		challengeInstanceSubmission.setTimeTaken(challengeInstanceSubmission.getSubmissionTime().getTime()- challengeInstanceSubmission.getStartTime().getTime());
		challengeInstanceSubmissionRepository.save(challengeInstanceSubmission);
		executor.execute(new ProblemEvaluationTask(problemEvaluationService,challengeInstanceSubmission));
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
	public ChallengeInstance updateChallengeInstance(ChallengeInstance challengeInstanceForm) {
		ChallengeInstance challengeInstance = getChallengeInstance(challengeInstanceForm.getId());
		challengeInstance.setName(challengeInstanceForm.getName());
		challengeInstance.setType(challengeInstanceForm.getType());
		challengeInstance.setStartDate(challengeInstanceForm.getStartDate());
		challengeInstance.setEndDate(challengeInstanceForm.getEndDate());
		return challengeInstanceRepository.save(challengeInstance);
	}

	@Override
	public ChallengeInstance startChallengeInstance(Long instanceId) {
		ChallengeInstance challengeInstance = getChallengeInstance(instanceId);
		challengeInstance.setInstanceStatus(ChallengeInstanceStatus.LIVE);
		return challengeInstanceRepository.save(challengeInstance);
	}

	@Override
	public ChallengeInstance stopChallengeInstance(Long instanceId) {
		ChallengeInstance challengeInstance = getChallengeInstance(instanceId);
		challengeInstance.setInstanceStatus(ChallengeInstanceStatus.CREATED);
		return challengeInstanceRepository.save(challengeInstance);
	}

	@Override
	public void deleteChallengeInstance(Long challengeInstanceId) {
		List<Problem> problemList = problemService.getProblems(challengeInstanceId);
		if(problemList!=null && !problemList.isEmpty()){
			problemService.deleteProblems(problemList.stream().map(t-> t.getId()).collect(Collectors.toList()));
		}
		challengeInstanceRepository.deleteById(challengeInstanceId);
	}

	@Override
	public List<UserSubmission> getChallengeInstanceSubmissions(Long challengeInstanceId) {
		List<UserSubmission> submissionList = new ArrayList<>();
		List<ChallengeInstanceSubmission> challengeInstanceSubmissions = challengeInstanceSubmissionRepository.findByChallengeInstanceIdAndSubmissionStatus(challengeInstanceId, SubmissionStatus.SUBMITTED);
		if (challengeInstanceSubmissions != null) {
			for (ChallengeInstanceSubmission challengeInstanceSubmission : challengeInstanceSubmissions) {
				UserSubmission submissionDetails = new UserSubmission(challengeInstanceSubmission);
				List<ProblemSolution> problemSolutions = problemSolutionService.getProblemSolutions(challengeInstanceSubmission.getUserId(),challengeInstanceSubmission.getChallengeInstanceId());
				EvaluationStatus evaluationStatus = EvaluationStatus.IN_PROGRESS;
				if(problemSolutions !=null){
					List<ProblemSolution> completedSolutionsList = problemSolutions.stream().filter(t-> EvaluationStatus.COMPLETED.equals(t.getEvaluationStatus())).collect(Collectors.toList());
					Double totalPoints = completedSolutionsList.stream().mapToDouble(t-> t.getPoints()).sum();
					submissionDetails.setTotalPoints(totalPoints);
					if(completedSolutionsList.size()==problemSolutions.size()){
						evaluationStatus = EvaluationStatus.COMPLETED;
					}
					submissionDetails.setEvaluationStatus(evaluationStatus);
				}
				User user = userService.getUserById(challengeInstanceSubmission.getUserId()).get();
				UserProfile userProfile = new UserProfile();
				userProfile.setFullName(user.getFullName());
				userProfile.setGender(userProfile.getGender());
				submissionDetails.setUserDetails(userProfile);
				submissionList.add(submissionDetails);
			}
		}
		return submissionList;
	}

	@Override
	public List<EvaluateProblem> getSubmittedProblems(Long challengeInstanceSubmissionId) {
		List<EvaluateProblem> evaluateProblems = new ArrayList<>();
		List<ProblemSolution> problemSolutionList = problemSolutionService.getProblemSolutions(challengeInstanceSubmissionId);
		if(problemSolutionList!=null){
			for(ProblemSolution problemSolution: problemSolutionList){
				evaluateProblems.add(getEvaluateProblem(problemSolution));
			}
		}
		return evaluateProblems;
	}

	private EvaluateProblem getEvaluateProblem(ProblemSolution problemSolution){
		EvaluateProblem evaluateProblem = new EvaluateProblem();
		evaluateProblem.setProblemSolution(problemSolution);
		Problem problem = problemService.getProblem(problemSolution.getProblemId());
		evaluateProblem.setName(problem.getName());
		evaluateProblem.setType(problem.getType());
		if(problemSolution.getLanguageId()!=null){
			Language language = languageService.getLanguage(problemSolution.getLanguageId());
			evaluateProblem.setLanguage(language);
		}
		return evaluateProblem;
	}

	@Override
	public EvaluateProblem updateProblemSolution(ProblemSolution problemSolutionInput) {
		ProblemSolution problemSolution = problemSolutionService.getProblemSolution(problemSolutionInput.getId());
		problemSolution.setEvaluationStatus(problemSolutionInput.getEvaluationStatus());
		if(EvaluationStatus.IN_PROGRESS.equals(problemSolutionInput.getEvaluationStatus())){
			problemSolution.setEvaluationRemarks(null);
			problemSolution.setSolutionResult(null);
		}else{
			problemSolution.setEvaluationRemarks(problemSolutionInput.getEvaluationRemarks());
			problemSolution.setSolutionResult(problemSolutionInput.getSolutionResult());
		}
		if(SolutionResult.FAIL.equals(problemSolutionInput.getSolutionResult())){
			problemSolution.setPoints(0f);
		}else{
			problemSolution.setPoints(problemSolutionInput.getPoints());
		}
		problemSolutionService.updateSolution(problemSolution);
		return getEvaluateProblem(problemSolution);
	}

	private List<Leaderboard.ChallengeInstancePoints> getProblemSolutions(ChallengeInstance challengeInstance){
		List<Leaderboard.ChallengeInstancePoints> challengeInstancePoints = new ArrayList<>();
		List<ProblemSolution> problemInstanceSolutions = problemSolutionService.getProblemSolutionsByChallengeInstanceId(EvaluationStatus.COMPLETED,challengeInstance.getId());
		if(problemInstanceSolutions!=null){
			Map<Long, Set<ProblemSolution>> problemSolutionsByUser = problemInstanceSolutions.stream().collect(Collectors.groupingBy(ProblemSolution::getUserId, Collectors.toSet()));
			for (Map.Entry<Long,Set<ProblemSolution>> entry : problemSolutionsByUser.entrySet()){
				Leaderboard.ChallengeInstancePoints instancePoints = new Leaderboard.ChallengeInstancePoints();
				instancePoints.setUserId(entry.getKey());
				instancePoints.setChallengeInstanceId(challengeInstance.getId());
				Double points = entry.getValue().stream().mapToDouble(ProblemSolution::getPoints).sum();
				Double timeTaken = entry.getValue().stream().mapToLong(ProblemSolution::getTimeTaken).average().getAsDouble();
				instancePoints.setPoints(points);
				instancePoints.setTimeTaken(timeTaken.longValue());
				challengeInstancePoints.add(instancePoints);
			}
		}
		return challengeInstancePoints;
	}

	@Override
	public Leaderboard getChallengeLeaderboard(Long challengeId) {
		Leaderboard leaderboard = new Leaderboard();
		List<ChallengeInstance> challengeInstanceList = getChallengeInstanceList(challengeId);
		if(challengeInstanceList!=null){
			List<Leaderboard.ChallengeInstancePoints> challengeInstancePointsList = new ArrayList<>();
			for(ChallengeInstance challengeInstance : challengeInstanceList){
				List<Leaderboard.ChallengeInstancePoints> instancePoints = getProblemSolutions(challengeInstance);
				if(!instancePoints.isEmpty()){
					challengeInstancePointsList.addAll(instancePoints);
				}
			}
			if(!challengeInstancePointsList.isEmpty()){
				Map<Long, Set<Leaderboard.ChallengeInstancePoints>> problemSolutionsByUser = challengeInstancePointsList.stream().collect(Collectors.groupingBy(Leaderboard.ChallengeInstancePoints::getUserId, Collectors.toSet()));
				List<Leaderboard.UserSubmissions> userSubmissions = new ArrayList<>();
				for (Map.Entry<Long,Set<Leaderboard.ChallengeInstancePoints>> entry : problemSolutionsByUser.entrySet()){
					 Leaderboard.UserSubmissions submission = new Leaderboard.UserSubmissions();
					 UserProfile userProfile = UserProfile.getUserProfile(userService.getUserById(entry.getKey()).get());
					 Double points = entry.getValue().stream().mapToDouble(Leaderboard.ChallengeInstancePoints::getPoints).sum();
					 Long timeTaken = entry.getValue().stream().mapToLong(Leaderboard.ChallengeInstancePoints::getTimeTaken).sum();
					 submission.setTotalPoints(points);
					 submission.setTotalTimeTaken(timeTaken);
					 submission.setUserDetails(userProfile);
					 submission.setId(userProfile.getId());
					 submission.setChallengeInstancePoints(entry.getValue());
					 userSubmissions.add(submission);
				}
				Collections.sort(userSubmissions);
				leaderboard.setUserSubmissionsList(userSubmissions);
			}
			leaderboard.setChallengeInstanceList(challengeInstanceList);
		}
		return leaderboard;
	}

}
