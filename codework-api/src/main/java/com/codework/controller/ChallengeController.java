package com.codework.controller;

import com.codework.entity.ChallengeInstance;
import com.codework.entity.ChallengeInstanceSubmission;
import com.codework.entity.ChallengeSubscription;
import com.codework.enums.ChallengeSubscriptionStatus;
import com.codework.exception.BusinessException;
import com.codework.exception.SystemException;
import com.codework.model.ChallengeDetails;
import com.codework.model.ChallengeSubmitInput;
import com.codework.model.LiveChallengeDetails;
import com.codework.model.Response;
import com.codework.service.IChallengeInstanceService;
import com.codework.service.IChallengeService;
import com.codework.service.IChallengeSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "api/challenge")
@RestController
public class ChallengeController {

	@Autowired
	IChallengeService challengeService;

	@Autowired
	IChallengeInstanceService challengeInstanceService;

	@Autowired
	IChallengeSubscriptionService challengeSubscriptionService;

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
	 * Creates new challenge
	 * @param challengeDetails
	 * @return ChallengeDetails
	 */
	@PostMapping
	public Response<ChallengeDetails> createChallenge(@RequestBody ChallengeDetails challengeDetails) {
		return new Response<>(challengeService.createChallenge(challengeDetails).get());
	}

	/**
	 * Creates new challenge instance
	 * @param challengeInstance
	 * @return ChallengeInstance
	 */
	@PostMapping("/instance")
	public Response<ChallengeInstance> createChallengeInstance(@RequestBody ChallengeInstance challengeInstance) {
		return new Response<>(challengeInstanceService.createChallengeInstance(challengeInstance));
	}

	/**
	 * Updates existing challenge istance
	 * @param challengeInstance
	 * @return ChallengeInstance
	 */
	@PutMapping("/instance")
	public Response<ChallengeInstance> updateChallengeInstance(@RequestBody ChallengeInstance challengeInstance) {
		return new Response<>(challengeInstanceService.updateChallengeInstance(challengeInstance));
	}

	/**
	 * start challenge instance
	 * @param challengeInstanceId
	 * @return ChallengeInstance
	 */
	@PutMapping("/instance/{challengeInstanceId}/start")
	public Response<ChallengeInstance> startChallengeInstance(@PathVariable Long challengeInstanceId) {
		return new Response<>(challengeInstanceService.startChallengeInstance(challengeInstanceId));
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
	@GetMapping(value = "/{challengeInstanceId}/start")
	public Response<LiveChallengeDetails> startChallenge(@PathVariable Long challengeInstanceId) throws BusinessException {
		ChallengeInstanceSubmission challengeInstanceSubmission = challengeInstanceService.startChallenge(challengeInstanceId,1l);
		LiveChallengeDetails challengeDetails = challengeService.getLiveChallengeDetails(challengeInstanceSubmission.getChallengeId(),1l);
		return new Response<>(challengeDetails);
	}

	/**
	 * submit challenge
	 * @param submitInput
	 * @return Challenge
	 */
	@PostMapping(value = "/{challengeId}/submit")
	public Response<ChallengeDetails> submitChallenge(@RequestBody ChallengeSubmitInput submitInput) throws SystemException, BusinessException {
		ChallengeInstanceSubmission challengeInstanceSubmission = challengeInstanceService.submitChallenge(submitInput, 1l);
		ChallengeDetails challengeDetails = challengeService.getChallenge(challengeInstanceSubmission.getChallengeId(),1l).get();
		return new Response<>(challengeDetails);
	}
}
