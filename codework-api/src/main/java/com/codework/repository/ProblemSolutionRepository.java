package com.codework.repository;

import com.codework.entity.ProblemSolution;
import com.codework.enums.EvaluationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProblemSolutionRepository extends MongoRepository<ProblemSolution, Long>{

    Optional<ProblemSolution> findByUserIdAndProblemId(Long userId, Long problemId);

    List<ProblemSolution> findByUserIdAndChallengeInstanceId(Long userId, Long challengeInstanceId);

    List<ProblemSolution> findByChallengeInstanceSubmissionId(Long challengeInstanceSubmissionId);

    List<ProblemSolution> findByEvaluationStatusAndChallengeInstanceId(EvaluationStatus evaluationStatus, Long challengeInstanceId);

    List<ProblemSolution> findByEvaluationStatus(EvaluationStatus evaluationStatus);
}
