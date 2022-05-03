package com.codework.repository;

import com.codework.entity.ChallengeInstance;
import com.codework.entity.ChallengeInstanceSubmission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChallengeInstanceSubmissionRepository extends MongoRepository<ChallengeInstanceSubmission, Long>{

    Optional<ChallengeInstanceSubmission> findByChallengeInstanceIdAndUserId(Long challengeInstanceId, Long userId);
}
