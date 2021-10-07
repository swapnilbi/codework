package com.example.codework.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.codework.entity.Challenge;
import com.example.codework.repo.ChallengeRepository;
import com.example.codework.service.IService;

@Service
public class ChallengeService implements IService {
	@Autowired
	ChallengeRepository repo;
	
	public Optional<Challenge> getChallenge(int id) {
		return repo.findById(id);
	}
	
	public List<Challenge> getChallenges() {
		return repo.findAll();
	}
	
	public Challenge createChallenge(Challenge c) {
		return repo.save(c);
	}
	
	public Optional<Challenge> registerChallenge(int id) {
		return null;
	}
}
