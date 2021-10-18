package com.codework.controller;

import com.codework.entity.ChallengeSubscription;
import com.codework.enums.ChallengeSubscriptionStatus;
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
	@GetMapping(value = "/{id}")
	public Response<ChallengeDetails> getChallenge(@PathVariable Long id) {
		return new Response<>(challengeService.getChallenge(id).get());
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
	 */
	@PostMapping(value = "/{id}/register")
	public Response<ChallengeSubscription> registerChallenge(@PathVariable Long id) {
		return new Response<>(challengeSubscriptionService.registerChallenge(id, ChallengeSubscriptionStatus.REGISTERED).get());
	}
	
	/**
	 * start challenge
	 * @param id
	 * @return Challenge
	 */
	@PostMapping(value = "/{id}/start")
	public Response<ChallengeSubscription> startChallenge(@PathVariable Long challengeId) {
		return new Response<>(challengeSubscriptionService.startChallenge(challengeId).get());
	}
	
	/**
	 * submit challenge
	 * @param id
	 * @return Challenge
	 */
	@PostMapping(value = "/{id}/")
	public Response<ChallengeSubscription> submitChallenge(@PathVariable Long challengeId) {
		return new Response<>(challengeSubscriptionService.submitChallenge(challengeId).get());
	}
}
