package com.codework.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codework.entity.Challenge;
import com.codework.repository.ChallengeRepository;
import com.codework.service.IChallengeService;

@Service
public class ChallengeService implements IChallengeService {

	@Autowired
	ChallengeRepository repository;

	@Override
	public Optional<Challenge> getChallenge(int id) {
		return repository.findById(id);
	}

	@Override
	public List<Challenge> getChallenges() {
		return repository.findAll();
	}

	@Override
	public Challenge createChallenge(Challenge c) {
		return repository.save(c);
	}

	@Override
	public Optional<Challenge> registerChallenge(int id) {
		return null;
	}

}
