package com.codework.repository;

import com.codework.entity.ProblemSolution;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProblemSolutionRepository extends MongoRepository<ProblemSolution, Long>{
    Optional<ProblemSolution> findByUserIdAndProblemId(String userId, Long problemId);
}
