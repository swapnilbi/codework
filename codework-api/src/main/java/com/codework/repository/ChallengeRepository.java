package com.codework.repository;

import com.codework.entity.Challenge;
import com.codework.enums.ChallengeStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChallengeRepository extends MongoRepository<Challenge, Long>{
    List<Challenge> findByStatusIn(List<ChallengeStatus> challengeStatusList);
}
