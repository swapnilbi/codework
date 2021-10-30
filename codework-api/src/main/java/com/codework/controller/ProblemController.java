package com.codework.controller;

import java.util.List;

import com.codework.entity.Problem;
import com.codework.entity.ProblemSolution;
import com.codework.exception.BusinessException;
import com.codework.exception.SystemException;
import com.codework.model.ProblemSolutionInput;
import com.codework.model.ProblemSolutionResult;
import com.codework.service.IProblemSolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codework.model.ProblemDetails;
import com.codework.model.Response;
import com.codework.service.IProblemService;

import javax.validation.Valid;

@RequestMapping(value = "api/challenge")
@RestController
public class ProblemController {

	@Autowired
	IProblemService problemService;

	@Autowired
	IProblemSolutionService problemSolutionService;


	/**
	 * Returns all active Problems
	 * @return List<ProblemDetails>
	 */
	@GetMapping(value = "/{challengeId}/problems")
	public Response<List<ProblemDetails>> getProblems(@PathVariable Long challengeId) {
		return new Response<>(problemService.getProblems(challengeId));
	}

	/**
	 * Creates new problem
	 * @param problemDetails
	 * @return ProblemDetails
	 */
	@PostMapping(value = "/problem")
	public Response<Problem> createProblem(@Valid @RequestBody ProblemDetails problemDetails) {
		return new Response<>(problemService.createProblem(problemDetails));
	}

	/**
	 * Compile solution
	 *
	 */
	@PostMapping(value = "/solution/compile")
	public Response<ProblemSolutionResult> compileSolution(@Valid @RequestBody ProblemSolutionInput problemSolution) throws SystemException {
		return new Response<>(problemSolutionService.compileSolution(problemSolution));
	}

	/**
	 * Save solution
	 *
	 */
	@PostMapping(value = "/solution/save")
	public Response<ProblemSolution> saveSolution(@RequestBody ProblemSolutionInput problemSolution) throws SystemException {
		return new Response<ProblemSolution>(problemSolutionService.saveSolution(problemSolution));
	}

	/**
	 * Submit solution
	 *
	 */
	@PostMapping(value = "/solution/submit")
	public Response<ProblemSolution> submitSolution(@RequestBody ProblemSolutionInput problemSolution) throws SystemException, BusinessException {
		return new Response<ProblemSolution>(problemSolutionService.submitSolution(problemSolution));
	}

}
