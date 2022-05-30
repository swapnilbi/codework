package com.codework.controller;

import com.codework.entity.Challenge;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "api/challenge")
@RestController
public class ChallengeController {

	@Autowired
	IChallengeService challengeService;

	@Autowired
	IChallengeInstanceService challengeInstanceService;

	/**
	 * Get challenge details
	 * @param challengeId
	 * @return ChallengeDetails
	 */
	@GetMapping(value = "/manage/{challengeId}/start")
	public Response<Challenge> startChallenge(@PathVariable Long challengeId) {
		return new Response<>(challengeService.startChallenge(challengeId));
	}

	/**
	 * Get challenge details
	 * @param challengeId
	 * @return ChallengeDetails
	 */
	@GetMapping(value = "/manage/{challengeId}/stop")
	public Response<Challenge> stopChallenge(@PathVariable Long challengeId) {
		return new Response<>(challengeService.stopChallenge(challengeId));
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
	@GetMapping("/instance/{challengeInstanceId}/start")
	public Response<ChallengeInstance> startChallengeInstance(@PathVariable Long challengeInstanceId) {
		return new Response<>(challengeInstanceService.startChallengeInstance(challengeInstanceId));
	}

	/**
	 * start challenge instance
	 * @param challengeInstanceId
	 * @return ChallengeInstance
	 */
	@GetMapping("/instance/{challengeInstanceId}/stop")
	public Response<ChallengeInstance> stopChallengeInstance(@PathVariable Long challengeInstanceId) {
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
	 * Returns all active challenges
	 * @return List<ChallengeDetails>
	 */
	@GetMapping(value = "/manage/list")
	public Response<List<ChallengeDetails>> getChallengeList() {
		return new Response<>(challengeService.getChallenges());
	}


}
