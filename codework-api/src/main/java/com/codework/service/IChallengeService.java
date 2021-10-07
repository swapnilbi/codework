package com.codework.service;

import com.codework.entity.Challenge;

import java.util.List;
import java.util.Optional;

public interface IChallengeService {

    Optional<Challenge> getChallenge(int id);

    List<Challenge> getChallenges();

    Challenge createChallenge(Challenge c);

    Optional<Challenge> registerChallenge(int id);

}
