package com.codework.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	SequenceGenerator sequenceGenerator;
	
	@Autowired
	LanguageService languageService;
	
	
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
		List<ProblemDetails> problemDetailsList = new ArrayList<>();
		for(Problem problem : problemList) {
			ProblemDetails problemDetails = new ProblemDetails(problem);
			if(problem.getLanguagesAllowed()!=null) {
				problemDetails.setLanguagesAllowed(languageService.getLanguages(problem.getLanguagesAllowed()));
			}
			problemDetailsList.add(problemDetails);
		}
		return problemDetailsList;
	}

}