package com.codework.controller;

import com.codework.entity.ChallengeInstance;
import com.codework.entity.ProblemSolution;
import com.codework.exception.BusinessException;
import com.codework.exception.SystemException;
import com.codework.model.EvaluateProblem;
import com.codework.model.Response;
import com.codework.model.UserSubmission;
import com.codework.service.IChallengeInstanceService;
import com.codework.service.IProblemEvaluationService;
import com.codework.utility.SecurityHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "api/challenge")
@RestController
@Slf4j
public class ChallengeInstanceController {

	@Autowired
	IChallengeInstanceService challengeInstanceService;

	@Autowired
	IProblemEvaluationService problemEvaluationService;

	/**
	 * Creates new challenge instance
	 * @param challengeInstance
	 * @return ChallengeInstance
	 */
	@PostMapping("/instance")
	public Response<ChallengeInstance> createChallengeInstance(@RequestBody ChallengeInstance challengeInstance) {
		log.info("createChallengeInstance "+challengeInstance);
		return new Response<>(challengeInstanceService.createChallengeInstance(challengeInstance));
	}

	/**
	 * Updates existing challenge istance
	 * @param challengeInstance
	 * @return ChallengeInstance
	 */
	@PutMapping("/instance/{challengeInstanceId}")
	public Response<ChallengeInstance> updateChallengeInstance(@RequestBody ChallengeInstance challengeInstance) throws BusinessException {
		log.info("updateChallengeInstance "+challengeInstance);
		return new Response<>(challengeInstanceService.updateChallengeInstance(challengeInstance));
	}

	/**
	 * Deletes existing challenge instance
	 * @param challengeInstanceId
	 * @return ChallengeInstance
	 */
	@DeleteMapping("/instance/{challengeInstanceId}")
	public Response deleteChallengeInstance(@PathVariable Long challengeInstanceId) {
		log.info("deleteChallengeInstance "+challengeInstanceId);
		challengeInstanceService.deleteChallengeInstance(challengeInstanceId);
		return new Response<>();
	}

	/**
	 * start challenge instance
	 * @param challengeInstanceId
	 * @return ChallengeInstance
	 */
	@GetMapping("/instance/{challengeInstanceId}/publish")
	public Response<ChallengeInstance> startChallengeInstance(@PathVariable Long challengeInstanceId) throws BusinessException {
		log.info("startChallengeInstance "+challengeInstanceId);
		return new Response<>(challengeInstanceService.startChallengeInstance(challengeInstanceId));
	}

	/**
	 * view submissions
	 * @param challengeInstanceId
	 * @return ChallengeInstance
	 */
	@GetMapping("/instance/{challengeInstanceId}/submissions")
	public Response<List<UserSubmission>> getChallengeInstanceSubmission(@PathVariable Long challengeInstanceId) {
		return new Response<>(challengeInstanceService.getChallengeInstanceSubmissions(challengeInstanceId));
	}

	/**
	 * get problems associated with submission
	 * @param challengeInstanceSubmissionId
	 * @return ChallengeInstance
	 */
	@GetMapping("/instance/submission/{challengeInstanceSubmissionId}/problems")
	public Response<List<EvaluateProblem>> getSubmittedProblems(@PathVariable Long challengeInstanceSubmissionId) {
		return new Response<>(challengeInstanceService.getSubmittedProblems(challengeInstanceSubmissionId));
	}

	/**
	 * update problem solution
	 * @param problemSolution
	 * @return ChallengeInstance
	 */
	@PostMapping("/instance/submission/problem/{problemSubmissionId}")
	public Response<EvaluateProblem> updateProblemSolution(@RequestBody ProblemSolution problemSolution) {
		return new Response<>(challengeInstanceService.updateProblemSolution(problemSolution,SecurityHelper.getUserId()));
	}

	/**
	 * reset problem solution
	 * @param problemSolutionId
	 * @return ChallengeInstance
	 */
	@DeleteMapping("/instance/submission/problem/{problemSolutionId}/reset")
	public Response resetProblemSolution(@PathVariable Long problemSolutionId) {
		log.info("resetProblemSolution "+problemSolutionId);
		challengeInstanceService.resetProblemSolution(problemSolutionId);
		return new Response<>();
	}


	/**
	 * start challenge instance
	 * @param challengeInstanceId
	 * @return ChallengeInstance
	 */
	@GetMapping("/instance/{challengeInstanceId}")
	public Response<ChallengeInstance> getChallengeInstance(@PathVariable Long challengeInstanceId) throws BusinessException {
		return new Response<>(challengeInstanceService.getChallengeInstance(challengeInstanceId));
	}

	/**
	 * start challenge instance
	 * @param challengeInstanceId
	 * @return ChallengeInstance
	 */
	@GetMapping("/instance/{challengeInstanceId}/stop")
	public Response<ChallengeInstance> stopChallengeInstance(@PathVariable Long challengeInstanceId) throws BusinessException {
		log.info("stopChallengeInstance "+challengeInstanceId);
		return new Response<>(challengeInstanceService.stopChallengeInstance(challengeInstanceId));
	}

	/**
	 * submit challenge
	 * @param challengeId
	 * @return Challenge
	 */
	@GetMapping(value = "/{challengeId}/instance/list")
	public Response<List<ChallengeInstance>> getChallengeInstanceList(@PathVariable Long challengeId) throws SystemException {
		List<ChallengeInstance> challengeInstanceList = challengeInstanceService.getChallengeInstanceList(challengeId);
		return new Response<>(challengeInstanceList);
	}

	/**
	 * evaluate challenge
	 *
	 */
	@PostMapping(value = "/instance/{challengeInstanceId}/submissions/evaluate")
	public void evaluateSolution(@PathVariable Long challengeInstanceId) throws IOException {
		log.info("evaluateSolution "+challengeInstanceId);
		problemEvaluationService.checkAllSubmissionResult(challengeInstanceId);
	}

	@PostMapping("/instance/{challengeInstanceId}/submissions/upload")
	public Response bulkUploadSolutions(@RequestParam("file") MultipartFile file, @PathVariable Long challengeInstanceId) throws BusinessException, ParseException {
		return challengeInstanceService.bulkUploadSolutions(file,challengeInstanceId);
	}

}
