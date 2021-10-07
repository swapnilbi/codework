package com.codework.service;

import com.codework.model.ChallengeDetails;

import java.util.List;
import java.util.Optional;

public interface IChallengeService {

    Optional<ChallengeDetails> getChallenge(long id);

    List<ChallengeDetails> getChallenges();

    Optional<ChallengeDetails> createChallenge(ChallengeDetails challengeInput);

}
