package com.codework.service.impl;

import com.codework.entity.*;
import com.codework.enums.SubmissionStatus;
import com.codework.model.ProblemDetails;
import com.codework.model.TestCase;
import com.codework.repository.ProblemRepository;
import com.codework.repository.SequenceGenerator;
import com.codework.service.IChallengeInstanceService;
import com.codework.service.ILanguageService;
import com.codework.service.IProblemService;
import com.codework.service.IProblemSolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProblemService implements IProblemService {

	@Autowired
	ProblemRepository problemRepository;

	@Autowired
	IProblemSolutionService problemSolutionService;

	@Autowired
	SequenceGenerator sequenceGenerator;
	
	@Autowired
	ILanguageService languageService;

	@Autowired
	IChallengeInstanceService challengeInstanceService;

	@Override
	public Optional<ProblemDetails> getProblemDetails(Long problemId) {
		Optional<Problem> problem = problemRepository.findById(problemId);
		 if(problem.isPresent()) {
			 ProblemDetails problemDetails = new ProblemDetails(problem.get());
			 if (problem.get().getLanguagesAllowed() != null) {
				 problemDetails.setLanguagesAllowed(languageService.getLanguages(problem.get().getLanguagesAllowed()));
			 }
			 return Optional.of(problemDetails);
		 }
		 return Optional.empty();	
	}

	@Override
	public Problem getProblem(Long problemId) {
		Optional<Problem> problem = problemRepository.findById(problemId);
		return problem.get();
	}


	@Override
	public Problem createProblem(ProblemDetails problemDetails) {
		ChallengeInstance challengeInstance = challengeInstanceService.getChallengeInstance(problemDetails.getChallengeInstanceId());
		Problem problem = new Problem();
		List<Integer> languages = new ArrayList<>();
		problem.setId(sequenceGenerator.generateSequence(Problem.SEQUENCE_NAME));
		problem.setName(problemDetails.getName());
		problem.setChallengeId(challengeInstance.getChallengeId());
		problem.setProblemStatement(problemDetails.getProblemStatement());
		problem.setType(problemDetails.getType());
		problem.setPointSystem(problemDetails.getPointSystem());
		if(problemDetails.getLanguagesAllowed()!=null) {
			languages = problemDetails.getLanguagesAllowed().stream().map(Language::getId).collect(Collectors.toList());
		}
		problem.setLanguagesAllowed(languages);
		if(problemDetails.getTestCases()!=null){
			long id = 0;
			for(TestCase testCase : problemDetails.getTestCases()){
				testCase.setId(id++);
			}
			problem.setTestCases(problemDetails.getTestCases());
		}
		problem.setChallengeInstanceId(problemDetails.getChallengeInstanceId());
		problem.setMemoryLimit(problemDetails.getMemoryLimit());
		problem.setCpuLimit(problemDetails.getCpuLimit());
		problem.setPlaceHolderSolution(problemDetails.getPlaceHolderSolution());
		problem.setCreatedAt(new Date());
		//problem.setCreatedBy(problemDetails.getCreatedBy());
		problemRepository.save(problem);
		return problem;
	}

	@Override
	public void deleteProblem(Long problemId) {
		problemRepository.deleteById(problemId);
	}

	@Override
	public List<Language> getLanguages() {
		return languageService.getActiveLanguages();
	}

	@Override
	public void deleteProblems(List<Long> problemIds) {
		problemRepository.deleteAllById(problemIds);
	}

	@Override
	public Problem updateProblem(ProblemDetails problemDetails) {
		Problem problem = problemRepository.findById(problemDetails.getId()).get();
		List<Integer> languages = new ArrayList<>();
		problem.setName(problemDetails.getName());
		problem.setProblemStatement(problemDetails.getProblemStatement());
		problem.setType(problemDetails.getType());
		if(problemDetails.getLanguagesAllowed()!=null) {
			languages = problemDetails.getLanguagesAllowed().stream().map(Language::getId).collect(Collectors.toList());
		}
		if(problemDetails.getTestCases()!=null){
			long id = 0;
			for(TestCase testCase : problemDetails.getTestCases()){
				testCase.setId(id++);
			}
			problem.setTestCases(problemDetails.getTestCases());
		}
		problem.setPointSystem(problemDetails.getPointSystem());
		problem.setLanguagesAllowed(languages);
		problem.setTestCases(problemDetails.getTestCases());
		problem.setMemoryLimit(problemDetails.getMemoryLimit());
		problem.setCpuLimit(problemDetails.getCpuLimit());
		problem.setPlaceHolderSolution(problemDetails.getPlaceHolderSolution());
		problemRepository.save(problem);
		return problem;
	}

	@Override
	public List<Problem> getProblems(Long challengeInstanceId) {
		return problemRepository.findByChallengeInstanceId(challengeInstanceId);
	}

	@Override
	public List<ProblemDetails> getProblems(Long challengeInstanceId, Long userId) {
		List<Problem> problemList = getProblems(challengeInstanceId);
		Optional<ChallengeInstanceSubmission> challengeInstanceSubmission = challengeInstanceService.getChallengeInstanceSubmission(challengeInstanceId,userId);
		boolean isChallengeStarted = challengeInstanceSubmission.isPresent() ? challengeInstanceSubmission.get().getSubmissionStatus().equals(SubmissionStatus.IN_PROGRESS) : false;
		List<ProblemDetails> problemDetailsList = new ArrayList<>();
		for(Problem problem : problemList) {
			ProblemDetails problemDetails = new ProblemDetails(problem);
			if(problem.getLanguagesAllowed()!=null) {
				problemDetails.setLanguagesAllowed(languageService.getLanguages(problem.getLanguagesAllowed()));
			}
			if(isChallengeStarted){
				Optional<ProblemSolution> problemSolution = problemSolutionService.getProblemSolution(userId,problem.getId());
				if(problemSolution.isPresent()){
					problemDetails.setProblemSolution(problemSolution.get());
				}
			}
			problemDetailsList.add(problemDetails);
		}
		return problemDetailsList;
	}

}
