package com.codework.service.impl;

import com.codework.common.Constants;
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
import com.codework.utility.BulkUploadSolutionUtility;
import com.codework.utility.DateUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
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
	@Qualifier("evaluationTaskExecutor")
	private TaskExecutor executor;

	@Override
	public ChallengeInstanceSubmission startChallenge(Long challengeInstanceId, Long userId) throws BusinessException {
		ChallengeInstance challengeInstance = challengeInstanceRepository.findById(challengeInstanceId).get();
		validateChallengeStatus(challengeInstance,userId);
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

	private void validateChallengeStatus(ChallengeInstance challengeInstance, Long userId) throws BusinessException {
		if(challengeInstance.getInstanceStatus() == ChallengeInstanceStatus.EXPIRED){
			throw new BusinessException("Sorry! This Challenge is expired");
		}
		if(challengeInstance.getInstanceStatus().equals(ChallengeInstanceStatus.INACTIVE)){
			if(!userService.isBetaUser(userId)){
				throw new BusinessException("This challenge is open for only Beta user");
			}
		}
	}

	@Override
	public ChallengeInstanceSubmission submitChallenge(ChallengeSubmitInput submitInput, Long userId) throws SystemException, BusinessException {
		ChallengeInstance challengeInstance = getChallengeInstance(submitInput.getChallengeInstanceId());
		validateChallengeStatus(challengeInstance,userId);
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
	public List<ChallengeInstance> getChallengeInstanceListByStatus(Long challengeId, List<ChallengeInstanceStatus> challengeInstanceStatuses) {
		return challengeInstanceRepository.findByChallengeIdAndInstanceStatusIn(challengeId,challengeInstanceStatuses);
	}

	@Override
	public ChallengeInstance getChallengeInstance(Long id) throws BusinessException {
		Optional<ChallengeInstance> challengeInstance = challengeInstanceRepository.findById(id);
		if(!challengeInstance.isPresent()){
			throw new BusinessException("Challenge does not found");
		}
		return challengeInstance.get();
	}

	@Override
	public ChallengeInstance createChallengeInstance(ChallengeInstance challengeInstance) {
		challengeInstance.setInstanceStatus(ChallengeInstanceStatus.INACTIVE);
		challengeInstance.setId(sequenceGenerator.generateSequence(ChallengeInstance.SEQUENCE_NAME));
		return challengeInstanceRepository.save(challengeInstance);
	}

	@Override
	public ChallengeInstance updateChallengeInstance(ChallengeInstance challengeInstanceForm) throws BusinessException {
		ChallengeInstance challengeInstance = getChallengeInstance(challengeInstanceForm.getId());
		challengeInstance.setName(challengeInstanceForm.getName());
		challengeInstance.setType(challengeInstanceForm.getType());
		challengeInstance.setStartDate(challengeInstanceForm.getStartDate());
		challengeInstance.setEndDate(challengeInstanceForm.getEndDate());
		return challengeInstanceRepository.save(challengeInstance);
	}

	@Override
	public ChallengeInstance startChallengeInstance(Long instanceId) throws BusinessException {
		ChallengeInstance challengeInstance = getChallengeInstance(instanceId);
		ChallengeInstanceStatus challengeInstanceStatus = ChallengeInstanceStatus.CREATED;
		Date currentDate = DateUtility.currentDate();
		if(currentDate.after(challengeInstance.getStartDate()) && currentDate.before(challengeInstance.getEndDate())){
			challengeInstanceStatus = ChallengeInstanceStatus.LIVE;
		}
		challengeInstance.setInstanceStatus(challengeInstanceStatus);
		return challengeInstanceRepository.save(challengeInstance);
	}

	@Override
	public ChallengeInstance stopChallengeInstance(Long instanceId) throws BusinessException {
		ChallengeInstance challengeInstance = getChallengeInstance(instanceId);
		challengeInstance.setInstanceStatus(ChallengeInstanceStatus.INACTIVE);
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
		List<ChallengeInstanceSubmission> challengeInstanceSubmissions = challengeInstanceSubmissionRepository.findByChallengeInstanceId(challengeInstanceId);
		if (challengeInstanceSubmissions != null) {
			for (ChallengeInstanceSubmission challengeInstanceSubmission : challengeInstanceSubmissions) {
				UserSubmission submissionDetails = new UserSubmission(challengeInstanceSubmission);
				if(Arrays.asList(SubmissionStatus.SUBMITTED,SubmissionStatus.EXPIRED).contains(challengeInstanceSubmission.getSubmissionStatus())){
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
				}
				User user = userService.getUserById(challengeInstanceSubmission.getUserId()).get();
				submissionDetails.setUserDetails(UserProfile.getUserProfile(user));
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

	@Override
	public List<EvaluateProblem> getUserSubmittedProblems(Long challengeInstanceSubmissionId, Long userId) throws BusinessException {
		ChallengeInstanceSubmission challengeInstanceSubmission = challengeInstanceSubmissionRepository.findById(challengeInstanceSubmissionId).get();
		if(!SubmissionStatus.SUBMITTED.equals(challengeInstanceSubmission.getSubmissionStatus())) {
			throw new BusinessException("Solution is not submitted yet");
		}
		ChallengeInstance challengeInstance = getChallengeInstance(challengeInstanceSubmission.getChallengeInstanceId());
		if(!userId.equals(challengeInstanceSubmission.getUserId()) && ChallengeInstanceStatus.EXPIRED.equals(challengeInstance.getInstanceStatus())) {
			throw new BusinessException("Challenge is not completed yet");
		}
		List<EvaluateProblem> evaluateProblems = getSubmittedProblems(challengeInstanceSubmissionId);
		if(evaluateProblems!=null && !evaluateProblems.isEmpty()){
			if(!ChallengeInstanceStatus.EXPIRED.equals(challengeInstance.getInstanceStatus())) {
				for(EvaluateProblem evaluateProblem: evaluateProblems){
					if(evaluateProblem.getProblemSolution()!=null){
						evaluateProblem.getProblemSolution().setTestCaseResults(null);
						evaluateProblem.getProblemSolution().setEvaluationRemarks(null);
					}
				}
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
		if(problemSolution.getEvaluatedBy()!=null){
			User user = userService.getUserById(problemSolution.getUserId()).get();
			evaluateProblem.setEvaluatedBy(user.getFullName());
		}
		if(problemSolution.getLanguageId()!=null){
			Language language = languageService.getLanguage(problemSolution.getLanguageId());
			evaluateProblem.setLanguage(language);
		}
		return evaluateProblem;
	}

	@Override
	public EvaluateProblem updateProblemSolution(ProblemSolution problemSolutionInput, Long userId) {
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
		problemSolution.setEvaluatedBy(userId);
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
	public EvaluationDetails getUserEvaluationDetails(ChallengeInstanceSubmission challengeInstanceSubmission){
		EvaluationDetails evaluationDetails = null;
		List<ProblemSolution> problemSolutionList = problemSolutionService.getProblemSolutions(challengeInstanceSubmission.getId());
		if(problemSolutionList!=null){
			evaluationDetails = new EvaluationDetails();
			if(problemSolutionList.stream().allMatch(t -> EvaluationStatus.COMPLETED.equals(t.getEvaluationStatus()))){
				Double points = problemSolutionList.stream().mapToDouble(ProblemSolution::getPoints).sum();
				evaluationDetails.setEvaluationStatus(EvaluationStatus.COMPLETED);
				evaluationDetails.setPoints(points);
			}
		}
		return evaluationDetails;
	}

	@Override
	public Leaderboard getChallengeLeaderboard(Long challengeId) {
		Leaderboard leaderboard = new Leaderboard();
		List<ChallengeInstance> challengeInstanceList = getChallengeInstanceListByStatus(challengeId,Arrays.asList(ChallengeInstanceStatus.EXPIRED,ChallengeInstanceStatus.CREATED,ChallengeInstanceStatus.LIVE));
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

	@Override
	public void resetProblemSolution(Long problemSolutionId) {
		log.info("resetProblemSolution "+problemSolutionId);
		ProblemSolution problemSolution = problemSolutionService.getProblemSolution(problemSolutionId);
		challengeInstanceSubmissionRepository.deleteById(problemSolution.getChallengeInstanceSubmissionId());
		problemSolutionService.deleteProblemSolution(problemSolutionId);
	}

	@Override
	public void finishChallengeInstance(ChallengeInstance challengeInstance) {
		log.info("finishChallengeInstance "+challengeInstance.getId());
		if(!challengeInstance.getInstanceStatus().equals(ChallengeInstanceStatus.EXPIRED)){
			List<ChallengeInstanceSubmission> inProgressSubmissions = challengeInstanceSubmissionRepository.findByChallengeInstanceIdAndSubmissionStatus(challengeInstance.getId(), SubmissionStatus.IN_PROGRESS);
			if(inProgressSubmissions!=null & !inProgressSubmissions.isEmpty()){
				for(ChallengeInstanceSubmission challengeInstanceSubmission : inProgressSubmissions){
					try {
						ChallengeSubmitInput challengeSubmitInput = new ChallengeSubmitInput();
						challengeSubmitInput.setChallengeInstanceId(challengeInstanceSubmission.getChallengeInstanceId());
						log.info("Auto submitting challenge instance submission "+challengeInstanceSubmission.getId());
						submitChallenge(challengeSubmitInput, challengeInstanceSubmission.getUserId());
					}catch (Exception exception){
						log.error("Exception while submitting solution "+challengeInstanceSubmission,exception);
					}
				}
			}
			challengeInstance.setInstanceStatus(ChallengeInstanceStatus.EXPIRED);
			challengeInstanceRepository.save(challengeInstance);
		}
	}

	@Override
	public Response bulkUploadSolutions(MultipartFile file, Long challengeInstanceId) throws BusinessException, ParseException {
		if(!Arrays.stream(Constants.TYPE_LIST).anyMatch(file.getContentType()::equals)){
			throw new BusinessException("Please upload excel file");
		}
		List<BulkUploadSubmission> bulkUploadSubmissions = BulkUploadSolutionUtility.getBulkSolutionList(file);
		if(bulkUploadSubmissions.isEmpty()){
			throw new BusinessException("Invalid data");
		}
		ChallengeInstance challengeInstance = getChallengeInstance(challengeInstanceId);
		List<Problem> problemList = problemService.getProblems(challengeInstanceId);
		if(problemList== null || problemList.isEmpty()){
			throw new BusinessException("Problem does not exist. Please create problem first");
		}
		Long problemId = problemList.get(0).getId();
		Response response = new Response();
		for(BulkUploadSubmission submission : bulkUploadSubmissions){
			Optional<User> userOptional = userService.getUserByUsername(submission.getUsername());
			if(!userOptional.isPresent()){
				response.addError(" User does not exist "+submission.getUsername());
			}else{
				User user = userOptional.get();
				Optional<ChallengeInstanceSubmission> challengeInstanceSubmissionOptional = getChallengeInstanceSubmission(challengeInstanceId,user.getId());
				ChallengeInstanceSubmission challengeInstanceSubmission = null;
				if(challengeInstanceSubmissionOptional.isPresent()){
					challengeInstanceSubmission = challengeInstanceSubmissionOptional.get();
				}else{
					challengeInstanceSubmission = new ChallengeInstanceSubmission();
					challengeInstanceSubmission.setId(sequenceGenerator.generateSequence(ChallengeInstanceSubmission.SEQUENCE_NAME));
					challengeInstanceSubmission.setChallengeId(challengeInstance.getChallengeId());
					challengeInstanceSubmission.setChallengeInstanceId(challengeInstanceId);
					challengeInstanceSubmission.setUserId(user.getId());
				}
				challengeInstanceSubmission.setStartTime(submission.getStartTime());
				challengeInstanceSubmission.setSubmissionTime(submission.getSubmissionTime());
				challengeInstanceSubmission.setSubmissionStatus(SubmissionStatus.SUBMITTED);
				challengeInstanceSubmission.setTimeTaken(submission.getTimeTaken());
				challengeInstanceSubmissionRepository.save(challengeInstanceSubmission);
				// save problem solution
				List<ProblemSolution> problemSolutionList = problemSolutionService.getProblemSolutions(challengeInstanceSubmission.getId());
				ProblemSolution problemSolution = null;
				if(problemSolutionList!=null && !problemSolutionList.isEmpty()){
					problemSolution = problemSolutionList.get(0);
				}else{
					problemSolution = new ProblemSolution();
					problemSolution.setId(sequenceGenerator.generateSequence(ProblemSolution.SEQUENCE_NAME));
					problemSolution.setChallengeInstanceSubmissionId(challengeInstanceSubmission.getId());
					problemSolution.setChallengeInstanceId(challengeInstance.getId());
					problemSolution.setCreatedAt(DateUtility.currentDate());
					problemSolution.setUserId(user.getId());
				}
				problemSolution.setProblemId(problemId);
				problemSolution.setTimeTaken(submission.getTimeTaken());
				problemSolution.setSubmittedAt(submission.getSubmissionTime());
				problemSolution.setEvaluationStatus(EvaluationStatus.COMPLETED);
				problemSolution.setPoints(submission.getPoints());
				problemSolution.setSolutionResult(submission.getSolutionResult());
				problemSolution.setEvaluationRemarks(submission.getRemarks());
				problemSolution.setSubmitted(true);
				problemSolution.setSolution(submission.getSolution());
				problemSolutionService.saveSolution(problemSolution);
			}
		}
		return response;
	}

}
