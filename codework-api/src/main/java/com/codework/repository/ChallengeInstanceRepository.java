package com.codework.repository;

import com.codework.entity.Challenge;
import com.codework.entity.ChallengeInstance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChallengeInstanceRepository extends MongoRepository<ChallengeInstance, Long>{

    List<ChallengeInstance> findByChallengeId(Long challengeId);

}
