package com.codework.repository;

import com.codework.entity.ChallengeInstance;
import com.codework.enums.ChallengeInstanceStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChallengeInstanceRepository extends MongoRepository<ChallengeInstance, Long>{

    List<ChallengeInstance> findByChallengeId(Long challengeId);

    List<ChallengeInstance> findByChallengeIdAndInstanceStatusIn(Long challengeId, List<ChallengeInstanceStatus> challengeInstanceStatuses);
}
