package com.codework.controller;

import com.codework.entity.ChallengeInstance;
import com.codework.exception.SystemException;
import com.codework.model.Response;
import com.codework.service.IChallengeInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(value = "api/challenge")
@RestController
public class ChallengeInstanceController {

	@Autowired
	IChallengeInstanceService challengeInstanceService;

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
	@PutMapping("/instance/{challengeInstanceId}")
	public Response<ChallengeInstance> updateChallengeInstance(@RequestBody ChallengeInstance challengeInstance) {
		return new Response<>(challengeInstanceService.updateChallengeInstance(challengeInstance));
	}

	/**
	 * Deletes existing challenge istance
	 * @param challengeInstanceId
	 * @return ChallengeInstance
	 */
	@DeleteMapping("/instance/{challengeInstanceId}")
	public Response deleteChallengeInstance(@PathVariable Long challengeInstanceId) {
		challengeInstanceService.deleteChallengeInstance(challengeInstanceId);
		return new Response<>();
	}

	/**
	 * start challenge instance
	 * @param challengeInstanceId
	 * @return ChallengeInstance
	 */
	@GetMapping("/instance/{challengeInstanceId}/publish")
	public Response<ChallengeInstance> startChallengeInstance(@PathVariable Long challengeInstanceId) {
		return new Response<>(challengeInstanceService.startChallengeInstance(challengeInstanceId));
	}

	/**
	 * start challenge instance
	 * @param challengeInstanceId
	 * @return ChallengeInstance
	 */
	@GetMapping("/instance/{challengeInstanceId}")
	public Response<ChallengeInstance> getChallengeInstance(@PathVariable Long challengeInstanceId) {
		return new Response<>(challengeInstanceService.getChallengeInstance(challengeInstanceId));
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


}
