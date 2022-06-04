package com.codework.controller;

import com.codework.entity.ChallengeInstanceSubmission;
import com.codework.entity.ChallengeSubscription;
import com.codework.entity.ProblemSolution;
import com.codework.exception.BusinessException;
import com.codework.exception.SystemException;
import com.codework.model.*;
import com.codework.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
@RequestMapping(value = "api/challenge")
@RestController
public class LiveChallengeController {

	@Autowired
	IChallengeService challengeService;

	@Autowired
	IProblemService problemService;

	@Autowired
	IChallengeInstanceService challengeInstanceService;

	@Autowired
	IChallengeSubscriptionService challengeSubscriptionService;

	@Autowired
	IProblemSolutionService problemSolutionService;


	/**
	 * Get challenge details
	 * @param challengeId
	 * @return ChallengeDetails
	 */
	@GetMapping(value = "/{challengeId}")
	public Response<ChallengeDetails> getChallenge(@PathVariable Long challengeId) {
		return new Response<>(challengeService.getChallengeDetails(challengeId,1l).get());
	}

	/**
	 * Get live challenge details
	 * @param challengeInstanceId
	 * @return ChallengeDetails
	 */
	@GetMapping(value = "/live/{challengeInstanceId}")
	public Response<LiveChallengeDetails> getLiveChallengeDetails(@PathVariable Long challengeInstanceId) {
		return new Response<>(challengeService.getLiveChallengeDetails(challengeInstanceId,1l));
	}

	/**
	 * Returns all active challenges
	 * @return List<ChallengeDetails>
	 */
	@GetMapping(value = "/list")
	public Response<List<ChallengeDetails>> getChallenges() {
		return new Response<>(challengeService.getChallenges(1l));
	}

	/**
	 * register challenge
	 * @param challengeId
	 * @return Challenge
	 * @throws BusinessException
	 */
	@GetMapping(value = "/{challengeId}/register")
	public Response<ChallengeDetails> registerChallenge(@PathVariable Long challengeId) throws BusinessException {
		ChallengeSubscription challengeSubscription = challengeSubscriptionService.registerChallenge(challengeId, 1l).get();
		ChallengeDetails challengeDetails = challengeService.getChallenge(challengeId,1l).get();
		challengeDetails.setChallengeSubscription(challengeSubscription);
		return new Response<>(challengeDetails);
	}
	
	/**
	 * start challenge
	 * @param challengeInstanceId
	 * @return Challenge
	 */
	@GetMapping(value = "/instance/{challengeInstanceId}/start")
	public Response<LiveChallengeDetails> startUserChallenge(@PathVariable Long challengeInstanceId) throws BusinessException {
		ChallengeInstanceSubmission challengeInstanceSubmission = challengeInstanceService.startChallenge(challengeInstanceId,1l);
		LiveChallengeDetails challengeDetails = challengeService.getLiveChallengeDetails(challengeInstanceSubmission.getChallengeId(),1l);
		return new Response<>(challengeDetails);
	}

	/**
	 * submit challenge
	 * @param submitInput
	 * @return Challenge
	 */
	@PostMapping(value = "instance/{challengeInstanceId}/submit")
	public Response<ChallengeDetails> submitChallenge(@RequestBody ChallengeSubmitInput submitInput) throws SystemException, BusinessException {
		ChallengeInstanceSubmission challengeInstanceSubmission = challengeInstanceService.submitChallenge(submitInput, 1l);
		ChallengeDetails challengeDetails = challengeService.getChallenge(challengeInstanceSubmission.getChallengeId(),1l).get();
		return new Response<>(challengeDetails);
	}

	/**
	 * Returns all active Problems
	 * @return List<ProblemDetails>
	 */
	@GetMapping(value = "/instance/{challengeInstanceId}/problems")
	public Response<List<ProblemDetails>> getProblems(@PathVariable Long challengeInstanceId) {
		return new Response<>(problemService.getProblems(challengeInstanceId,1l));
	}

	/**
	 * Compile solution
	 *
	 */
	@PostMapping(value = "/solution/compile")
	public Response<ProblemSolutionResult> compileSolution(@Valid @RequestBody ProblemSolutionInput problemSolution) throws SystemException, BusinessException, IOException {
		return new Response<>(problemSolutionService.compileSolution(problemSolution, 1l));
	}

	/**
	 * Run All test cases
	 *
	 */
	@PostMapping(value = "/solution/run")
	public Response<ProblemSolutionResult> runAllTests(@Valid @RequestBody ProblemSolutionInput problemSolution) throws SystemException, BusinessException, IOException {
		return new Response<>(problemSolutionService.runAllTests(problemSolution, 1l));
	}

	/**
	 * Save solution
	 *
	 */
	@PostMapping(value = "/solution/save")
	public Response<ProblemSolution> saveSolution(@RequestBody ProblemSolutionInput problemSolution) throws SystemException, BusinessException {
		return new Response<ProblemSolution>(problemSolutionService.saveSolution(problemSolution, 1l));
	}

	/**
	 * Submit solution
	 *
	 */
	@PostMapping(value = "/solution/submit")
	public Response<ProblemSolution> submitSolution(@RequestBody ProblemSolutionInput problemSolution) throws SystemException, BusinessException {
		return new Response<ProblemSolution>(problemSolutionService.submitSolution(problemSolution, 1l));
	}

}
