package com.codework.repository;

import com.codework.entity.ChallengeInstanceSubmission;
import com.codework.enums.SubmissionStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChallengeInstanceSubmissionRepository extends MongoRepository<ChallengeInstanceSubmission, Long>{

    Optional<ChallengeInstanceSubmission> findByChallengeInstanceIdAndUserId(Long challengeInstanceId, Long userId);

    List<ChallengeInstanceSubmission> findByChallengeInstanceIdAndSubmissionStatus(Long challengeInstanceId, SubmissionStatus submissionStatus);

    List<ChallengeInstanceSubmission> findByChallengeInstanceId(Long challengeInstanceId);
}
