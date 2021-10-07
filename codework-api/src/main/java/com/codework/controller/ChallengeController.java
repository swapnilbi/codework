package com.codework.controller;

import com.codework.model.ChallengeDetails;
import com.codework.model.CreateChallenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codework.entity.Challenge;
import com.codework.model.Response;
import com.codework.service.impl.ChallengeService;

import java.util.List;

@RequestMapping(value = "api/challenge")
@RestController
public class ChallengeController {

	@Autowired
	ChallengeService serviceService;

	/**
	 * Get challenge details
	 * @param id
	 * @return ChallengeDetails
	 */
	@GetMapping(value = "/{id}")
	public Response<ChallengeDetails> getChallenge(@PathVariable String id) {
		return null;
	}

	/**
	 * Returns all active challenges
	 * @return List<ChallengeDetails>
	 */
	@GetMapping(value = "/list")
	public Response<List<ChallengeDetails>> getChallenges() {
		return null;
	}

	/**
	 * Creates new challenge
	 * @param createChallenge
	 * @return ChallengeDetails
	 */
	@PostMapping
	public Response<ChallengeDetails> createChallenge(@RequestBody CreateChallenge createChallenge) {
		/**
		 * create challenge
		 */
		return null;
	}

	/**
	 * register challenge
	 * @param id
	 * @return Challenge
	 */
	@PostMapping(value = "/{id}/register")
	public Response<ChallengeDetails> registerChallenge(@PathVariable String id) {
		return null;
	}
	
}
