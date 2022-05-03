package com.codework.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.codework.entity.Problem;

public interface ProblemRepository extends MongoRepository<Problem, Long>{

	public List<Problem> findByChallengeId(Long id);

	public List<Problem> findByChallengeInstanceId(Long id);
}
