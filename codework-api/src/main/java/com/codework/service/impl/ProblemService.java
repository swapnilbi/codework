package com.codework.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.codework.entity.ChallengeSubscription;
import com.codework.entity.ProblemSolution;
import com.codework.enums.ChallengeSubscriptionStatus;
import com.codework.service.IChallengeSubscriptionService;
import com.codework.service.ILanguageService;
import com.codework.service.IProblemSolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codework.entity.Language;
import com.codework.entity.Problem;
import com.codework.model.ProblemDetails;
import com.codework.repository.ProblemRepository;
import com.codework.repository.SequenceGenerator;
import com.codework.service.IProblemService;

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
	IChallengeSubscriptionService challengeSubscriptionService;
	
	
	@Override
	public Optional<ProblemDetails> getProblem(Long problemId) {
		Optional<Problem> problem = problemRepository.findById(problemId);
		 if(problem.isPresent()){
			 	return Optional.of(new ProblemDetails(problem.get()));
			 }
		 return Optional.empty();	
	}


	@Override
	public Problem createProblem(ProblemDetails problemDetails) {
		Problem problem = new Problem();
		List<Integer> languages = new ArrayList<>();
		problem.setId(sequenceGenerator.generateSequence(Problem.SEQUENCE_NAME));
		problem.setName(problemDetails.getName());
		problem.setChallengeId(problemDetails.getChallengeId());
		problem.setProblemStatement(problemDetails.getProblemStatement());
		problem.setType(problemDetails.getType());
		if(problemDetails.getLanguagesAllowed()!=null) {
			languages = problemDetails.getLanguagesAllowed().stream().map(Language::getId).collect(Collectors.toList());
		}
		problem.setLanguagesAllowed(languages);
		problem.setTestCases(problemDetails.getTestCases());
		problem.setStartDate(problemDetails.getStartDate());
		problem.setEndDate(problemDetails.getEndDate());
		problem.setMemoryLimit(problemDetails.getMemoryLimit());
		problem.setCpuLimit(problemDetails.getCpuLimit());
		problem.setPlaceHolderSolution(problemDetails.getPlaceHolderSolution());
		problem.setCreatedAt(problemDetails.getCreatedAt());
		problem.setCreatedBy(problemDetails.getCreatedBy());
		problemRepository.save(problem);
		return problem;
	}

	@Override
	public List<ProblemDetails> getProblems(Long challengeId) {
		List<Problem> problemList = problemRepository.findByChallengeId(challengeId);
		Optional<ChallengeSubscription> challengeSubscription = challengeSubscriptionService.getChallengeSubscription(challengeId,"1");
		boolean isChallengeStarted = challengeSubscription.isPresent() ? challengeSubscription.get().getStatus().equals(ChallengeSubscriptionStatus.STARTED) : false;
		List<ProblemDetails> problemDetailsList = new ArrayList<>();
		for(Problem problem : problemList) {
			ProblemDetails problemDetails = new ProblemDetails(problem);
			if(problem.getLanguagesAllowed()!=null) {
				problemDetails.setLanguagesAllowed(languageService.getLanguages(problem.getLanguagesAllowed()));
			}
			if(isChallengeStarted){
				Optional<ProblemSolution> problemSolution = problemSolutionService.getProblemSolution("1",problem.getId());
				if(problemSolution.isPresent()){
					problemDetails.setProblemSolution(problemSolution.get());
				}
			}
			problemDetailsList.add(problemDetails);
		}
		return problemDetailsList;
	}

}
