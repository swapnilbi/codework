package com.codework.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.codework.model.ChallengeDetails;
import com.codework.model.LiveChallengeDetails;

public interface IChallengeService {

    Optional<ChallengeDetails> getChallengeDetails(Long id, Long userId);

    Optional<ChallengeDetails> getChallenge(Long id, Long userId);

    List<ChallengeDetails> getChallenges(Long userId);

    Optional<ChallengeDetails> createChallenge(ChallengeDetails challengeInput);

    LiveChallengeDetails getLiveChallengeDetails(Long challengeInstanceId, Long userId);
}
