package com.codework.service;

import com.codework.entity.ChallengeInstance;
import com.codework.entity.ChallengeInstanceSubmission;
import com.codework.entity.ProblemSolution;
import com.codework.exception.BusinessException;
import com.codework.exception.SystemException;
import com.codework.model.ChallengeSubmitInput;
import com.codework.model.EvaluateProblem;
import com.codework.model.Leaderboard;
import com.codework.model.UserSubmission;

import java.util.List;
import java.util.Optional;

public interface IChallengeInstanceService {

    ChallengeInstanceSubmission startChallenge(Long challengeInstanceId, Long userId) throws BusinessException;

    ChallengeInstanceSubmission submitChallenge(ChallengeSubmitInput submitInput, Long userId) throws SystemException, BusinessException;

    Optional<ChallengeInstanceSubmission> getChallengeInstanceSubmission(Long challengeInstanceId, Long userId);

    ChallengeInstanceSubmission saveChallengeInstanceSubmission(ChallengeInstanceSubmission challengeInstanceSubmission);

    List<ChallengeInstance> getChallengeInstanceList(Long challengeId);

    ChallengeInstance getChallengeInstance(Long id);

    ChallengeInstance createChallengeInstance(ChallengeInstance challengeInstance);

    ChallengeInstance updateChallengeInstance(ChallengeInstance challengeInstance);

    ChallengeInstance startChallengeInstance(Long instanceId);

    ChallengeInstance stopChallengeInstance(Long instanceId);

    void deleteChallengeInstance(Long challengeInstanceId);

    List<UserSubmission> getChallengeInstanceSubmissions(Long challengeInstanceId);

    List<EvaluateProblem> getSubmittedProblems(Long challengeInstanceSubmissionId);

    EvaluateProblem updateProblemSolution(ProblemSolution problemSolution);

    Leaderboard getChallengeLeaderboard(Long challengeId);
}
