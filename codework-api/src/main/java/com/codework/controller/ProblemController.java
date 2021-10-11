package com.codework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codework.model.ProblemDetails;
import com.codework.model.Response;
import com.codework.service.impl.ProblemService;

@RequestMapping(value = "api/challenge/problem")
@RestController
public class ProblemController {

	@Autowired
	ProblemService problemService;

	/**
	 * Get problem details
	 * @param id
	 * @return ProblemDetails
	 */
	@GetMapping(value = "/{id}")
	public Response<List<ProblemDetails>> getProblems(@PathVariable Long id) {
		return new Response<>(problemService.getProblems(id));
	}

	/**
	 * Returns all active Problems
	 * @return List<ProblemDetails>
	 */
	@GetMapping(value = "/list")
	public Response<List<ProblemDetails>> getProblems() {
		return new Response<>(problemService.getProblems());
	}

	/**
	 * Creates new problem
	 * @param problemDetails
	 * @return ProblemDetails
	 */
	@PostMapping
	public Response<ProblemDetails> createProblem(@RequestBody ProblemDetails problemDetails) {
		return new Response<>(problemService.createProblem(problemDetails).get());
	}

}
