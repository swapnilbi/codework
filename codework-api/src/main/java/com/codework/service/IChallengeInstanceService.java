package com.codework.service;

import com.codework.entity.ChallengeInstance;
import com.codework.entity.ChallengeInstanceSubmission;
import com.codework.entity.ChallengeSubscription;
import com.codework.exception.BusinessException;
import com.codework.exception.SystemException;
import com.codework.model.ChallengeSubmitInput;

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
}
