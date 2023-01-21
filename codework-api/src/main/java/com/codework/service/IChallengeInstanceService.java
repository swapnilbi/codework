package com.codework.service;

import com.codework.entity.ChallengeInstance;
import com.codework.entity.ChallengeInstanceSubmission;
import com.codework.entity.ProblemSolution;
import com.codework.enums.ChallengeInstanceStatus;
import com.codework.exception.BusinessException;
import com.codework.exception.SystemException;
import com.codework.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface IChallengeInstanceService {

    ChallengeInstanceSubmission startChallenge(Long challengeInstanceId, Long userId) throws BusinessException;

    ChallengeInstanceSubmission submitChallenge(ChallengeSubmitInput submitInput, Long userId) throws SystemException, BusinessException;

    Optional<ChallengeInstanceSubmission> getChallengeInstanceSubmission(Long challengeInstanceId, Long userId);

    ChallengeInstanceSubmission saveChallengeInstanceSubmission(ChallengeInstanceSubmission challengeInstanceSubmission);

    List<ChallengeInstance> getChallengeInstanceList(Long challengeId);

    List<ChallengeInstance> getChallengeInstanceListByStatus(Long challengeId, List<ChallengeInstanceStatus> challengeInstanceStatuses);

    ChallengeInstance getChallengeInstance(Long id) throws BusinessException;

    ChallengeInstance createChallengeInstance(ChallengeInstance challengeInstance);

    ChallengeInstance updateChallengeInstance(ChallengeInstance challengeInstance) throws BusinessException;

    ChallengeInstance startChallengeInstance(Long instanceId) throws BusinessException;

    ChallengeInstance stopChallengeInstance(Long instanceId) throws BusinessException;

    void deleteChallengeInstance(Long challengeInstanceId);

    List<UserSubmission> getChallengeInstanceSubmissions(Long challengeInstanceId);

    List<EvaluateProblem> getSubmittedProblems(Long challengeInstanceSubmissionId);

    List<EvaluateProblem> getUserSubmittedProblems(Long challengeInstanceSubmissionId, Long userId) throws BusinessException;

    EvaluateProblem updateProblemSolution(ProblemSolution problemSolution, Long userId);

    EvaluationDetails getUserEvaluationDetails(ChallengeInstanceSubmission challengeInstanceSubmission);

    Leaderboard getChallengeLeaderboard(Long challengeId);

    void resetProblemSolution(Long problemSolutionId);

    void finishChallengeInstance(ChallengeInstance challengeInstance);

    Response bulkUploadSolutions(MultipartFile file, Long challengeInstanceId) throws BusinessException, ParseException;
}
