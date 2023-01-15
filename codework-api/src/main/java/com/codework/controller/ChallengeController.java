package com.codework.controller;

import com.codework.entity.Challenge;
import com.codework.model.ChallengeDetails;
import com.codework.model.Response;
import com.codework.service.IChallengeService;
import com.codework.utility.SecurityHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "api/challenge")
@RestController
@Slf4j
public class ChallengeController {

	@Autowired
	IChallengeService challengeService;

	/**
	 * Get challenge details
	 * @param challengeId
	 * @return ChallengeDetails
	 */
	@GetMapping(value = "/{challengeId}/publish")
	public Response<Challenge> startChallenge(@PathVariable Long challengeId) {
		log.info("startChallenge challengeId="+challengeId+" ");
		return new Response<>(challengeService.startChallenge(challengeId));
	}

	/**
	 * Get challenge details
	 * @param challengeId
	 * @return ChallengeDetails
	 */
	@GetMapping(value = "/{challengeId}/stop")
	public Response<Challenge> stopChallenge(@PathVariable Long challengeId) {
		log.info("stopChallenge challengeId="+challengeId+" ");
		return new Response<>(challengeService.stopChallenge(challengeId));
	}


	/**
	 * Creates new challenge
	 * @param challengeDetails
	 * @return ChallengeDetails
	 */
	@PostMapping
	public Response<ChallengeDetails> createChallenge(@RequestBody ChallengeDetails challengeDetails) {
		log.info("createChallenge "+challengeDetails);
		return new Response<>(challengeService.createChallenge(challengeDetails).get());
	}

	/**
	 * Creates new challenge
	 * @param challengeDetails
	 * @return ChallengeDetails
	 */
	@PutMapping("/{challengeId}")
	public Response<Challenge> updateChallenge(@RequestBody ChallengeDetails challengeDetails) {
		log.info("updateChallenge "+challengeDetails);
		return new Response<>(challengeService.updateChallenge(challengeDetails));
	}

	/**
	 * Deletes challenge
	 * @param challengeId
	 * @return ChallengeDetails
	 */
	@DeleteMapping("/{challengeId}")
	public Response deleteChallenge(@PathVariable Long challengeId) {
		log.info("deleteChallenge "+challengeId);
		challengeService.deleteChallenge(challengeId);
		return new Response<>();
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
