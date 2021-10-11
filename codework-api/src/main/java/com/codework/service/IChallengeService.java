package com.codework.service;

import java.util.List;
import java.util.Optional;

import com.codework.model.ChallengeDetails;

public interface IChallengeService {

    Optional<ChallengeDetails> getChallenge(long id);

    List<ChallengeDetails> getChallenges();

    Optional<ChallengeDetails> createChallenge(ChallengeDetails challengeInput);

}
