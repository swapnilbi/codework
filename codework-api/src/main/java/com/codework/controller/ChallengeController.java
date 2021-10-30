package com.codework.controller;

import com.codework.entity.ChallengeSubscription;
import com.codework.enums.ChallengeSubscriptionStatus;
import com.codework.exception.BusinessException;
import com.codework.model.ChallengeDetails;
import com.codework.model.Response;
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
	IChallengeSubscriptionService challengeSubscriptionService;

	/**
	 * Get challenge details
	 * @param id
	 * @return ChallengeDetails
	 */
	@GetMapping(value = "/{challengeId}")
	public Response<ChallengeDetails> getChallenge(@PathVariable Long challengeId) {
		return new Response<>(challengeService.getChallenge(challengeId).get());
	}

	/**
	 * Returns all active challenges
	 * @return List<ChallengeDetails>
	 */
	@GetMapping(value = "/list")
	public Response<List<ChallengeDetails>> getChallenges() {
		return new Response<>(challengeService.getChallenges());
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
	 * register challenge
	 * @param id
	 * @return Challenge
	 * @throws BusinessException 
	 */
	@GetMapping(value = "/{challengeId}/register")
	public Response<ChallengeDetails> registerChallenge(@PathVariable Long challengeId) throws BusinessException {
		ChallengeSubscription challengeSubscription = challengeSubscriptionService.registerChallenge(challengeId).get();
		ChallengeDetails challengeDetails = challengeService.getChallenge(challengeId).get();
		challengeDetails.setChallengeSubscription(challengeSubscription);
		return new Response<>(challengeDetails);
	}
	
	/**
	 * start challenge
	 * @param id
	 * @return Challenge
	 */
	@GetMapping(value = "/{challengeId}/start")
	public Response<ChallengeDetails> startChallenge(@PathVariable Long challengeId) {
		ChallengeSubscription challengeSubscription = challengeSubscriptionService.startChallenge(challengeId).get();
		ChallengeDetails challengeDetails = challengeService.getChallenge(challengeId).get();
		challengeDetails.setChallengeSubscription(challengeSubscription);
		return new Response<>(challengeDetails);
	}
	
	/**
	 * submit challenge
	 * @param id
	 * @return Challenge
	 */
	@PostMapping(value = "/{challengeId}/")
	public Response<ChallengeSubscription> submitChallenge(@PathVariable Long challengeId) {
		return new Response<>(challengeSubscriptionService.submitChallenge(challengeId).get());
	}

	/**
	 * submit challenge
	 * @param id
	 * @return Challenge
	 */
	@PostMapping(value = "/solution/run")
	public Response<ChallengeSubscription> runSolution(@PathVariable Long challengeId) {
		return new Response<>(challengeSubscriptionService.submitChallenge(challengeId).get());
	}
}
