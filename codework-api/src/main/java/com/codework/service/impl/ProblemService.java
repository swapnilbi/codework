package com.codework.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codework.entity.Problem;
import com.codework.model.ChallengeDetails;
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

	@Override
	public Optional<ProblemDetails> getProblem(long id) {
		Optional<Problem> problem = problemRepository.findById(id);
		 if(problem.isPresent()){
			 	return Optional.of(new ProblemDetails(problem.get()));
			 }
		 return Optional.empty();	
	}

	@Override
	public List<ProblemDetails> getProblems() {
		List<Problem> problemList = problemRepository.findAll();
		List<ProblemDetails> problemDetailsList = new ArrayList<>();
		for(Problem problem : problemList) {
			problemDetailsList.add(new ProblemDetails(problem));
		}
		return problemDetailsList;
	}

	@Override
	public Optional<ProblemDetails> createProblem(ProblemDetails problemDetails) {
		Problem problem = new Problem();
		problem.setName(problemDetails.getName());
		problemRepository.save(problem);
		return null;
	}

	public List<ProblemDetails> getProblems(Long id) {
		List<Problem> problemList = problemRepository.findByChallengeId(id);
		List<ProblemDetails> problemDetailsList = new ArrayList<>();
		for(Problem problem : problemList) {
			problemDetailsList.add(new ProblemDetails(problem));
		}
		return problemDetailsList;
	}

}
