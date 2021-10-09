package com.codework.service;

import com.codework.entity.SUBSCRIPTION_STATUS;
import com.codework.model.ChallengeDetails;

import java.util.List;
import java.util.Optional;

public interface IChallengeService {

    Optional<ChallengeDetails> getChallenge(long id);

    List<ChallengeDetails> getChallenges();

    Optional<ChallengeDetails> createChallenge(ChallengeDetails challengeInput);

    Optional<ChallengeDetails> registerChallenge(long id, SUBSCRIPTION_STATUS register);

}
