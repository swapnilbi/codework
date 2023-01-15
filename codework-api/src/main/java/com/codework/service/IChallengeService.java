package com.codework.service;

import com.codework.entity.Challenge;
import com.codework.exception.BusinessException;
import com.codework.model.ChallengeDetails;
import com.codework.model.LiveChallengeDetails;

import java.util.List;
import java.util.Optional;

public interface IChallengeService {

    Optional<ChallengeDetails> getChallengeDetails(Long id, Long userId) throws BusinessException;

    Optional<ChallengeDetails> getChallenge(Long id, Long userId);

    List<ChallengeDetails> getChallenges(Long userId);

    List<ChallengeDetails> getChallenges();

    Optional<ChallengeDetails> createChallenge(ChallengeDetails challengeInput);

    LiveChallengeDetails getLiveChallengeDetails(Long challengeInstanceId, Long userId) throws BusinessException;

    Challenge startChallenge(Long challengeId);

    Challenge stopChallenge(Long challengeId);

    Challenge updateChallenge(ChallengeDetails challengeDetails);

    void deleteChallenge(Long challengeId);

    void finishChallenge(Long id);
}
