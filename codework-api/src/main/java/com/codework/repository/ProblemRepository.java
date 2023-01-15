package com.codework.repository;

import com.codework.entity.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProblemRepository extends MongoRepository<Problem, Long>{

	public List<Problem> findByChallengeId(Long id);

	public List<Problem> findByChallengeInstanceId(Long id);
}
