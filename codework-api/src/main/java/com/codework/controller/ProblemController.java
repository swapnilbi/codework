package com.codework.controller;

import com.codework.entity.Language;
import com.codework.entity.Problem;
import com.codework.exception.BusinessException;
import com.codework.model.ProblemDetails;
import com.codework.model.Response;
import com.codework.service.ILanguageService;
import com.codework.service.IProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "api/problem")
@RestController
@Slf4j
public class ProblemController {

	@Autowired
	IProblemService problemService;

	@Autowired
	ILanguageService languageService;

	/**
	 * Creates new problem
	 * @param problemDetails
	 * @return ProblemDetails
	 */
	@PostMapping
	public Response<Problem> createProblem(@Valid @RequestBody ProblemDetails problemDetails) throws BusinessException {
		log.info("createProblem "+problemDetails);
		return new Response<>(problemService.createProblem(problemDetails));
	}

	/**
	 * updates existing problem
	 * @param problemDetails
	 * @return ProblemDetails
	 */
	@PutMapping("{problemId}")
	public Response<Problem> updateProblem(@Valid @RequestBody ProblemDetails problemDetails) {
		log.info("updateProblem "+problemDetails);
		return new Response<>(problemService.updateProblem(problemDetails));
	}

	@GetMapping("{instanceId}/list")
	public Response<List<Problem>> getProblems(@PathVariable Long instanceId) {
		return new Response<>(problemService.getProblems(instanceId));
	}

	@DeleteMapping("{problemId}")
	public Response deleteProblem(@PathVariable Long problemId) throws BusinessException {
		problemService.deleteProblem(problemId);
		return new Response<>();
	}

	@GetMapping("{problemId}")
	public Response<ProblemDetails> getProblem(@PathVariable Long problemId) {
		Optional<ProblemDetails> problemDetails = problemService.getProblemDetails(problemId);
		return new Response<>(problemDetails.get());
	}

	@GetMapping("languages")
	public Response<List<Language>> getLanguages() {
		return new Response<>(languageService.getLanguages());
	}



}
