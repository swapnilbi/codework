package com.example.codework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.codework.entity.Challenge;
import com.example.codework.model.Response;
import com.example.codework.service.impl.ChallengeService;

@RequestMapping(value = "challenge")
public class ControllerChallenge {
	@Autowired
	ChallengeService service;
	
	@GetMapping(value = "/{id}")
	public Response<Challenge> getChallenge(@PathVariable String id) {
		return null;
	}
	
	@GetMapping(value = "/all")
	public Response<Challenge> getChallenges() {
		return null;
	}
	
	@PostMapping(value = "/all")
	public ResponseEntity<Challenge> addChallenge(@RequestBody Challenge challenge) {
		service.createChallenge(challenge);
		return ResponseEntity.ok()
	            .body(challenge);
	}
	
	@PostMapping(value = "/register/{id}")
	public Response<Challenge> registerChallenge(@PathVariable String id) {
		return null;
	}
	
}
