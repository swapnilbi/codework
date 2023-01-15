package com.codework.scheduler;

import com.codework.entity.ChallengeInstance;
import com.codework.enums.ChallengeInstanceStatus;
import com.codework.enums.ChallengeStatus;
import com.codework.exception.BusinessException;
import com.codework.model.ChallengeDetails;
import com.codework.service.IChallengeInstanceService;
import com.codework.service.IChallengeService;
import com.codework.utility.DateUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class ChallengeStatusCron {

    @Autowired
    private IChallengeService challengeService;

    @Autowired
    private IChallengeInstanceService challengeInstanceService;

    @Scheduled(cron = "${judge0.challenge-status.updater.cron.expression}")
    public void updateChallengeStatus(){
        Date currentDate = DateUtility.currentDate();
        log.debug("updateChallengeStatus cron is running "+ currentDate);
        List<ChallengeDetails> challengeDetailsList = this.challengeService.getChallenges();
        if(challengeDetailsList!=null && !challengeDetailsList.isEmpty()){
               for(ChallengeDetails challengeDetails : challengeDetailsList){
                   if(!challengeDetails.getStatus().equals(ChallengeStatus.INACTIVE)){
                       if(!challengeDetails.getStatus().equals(ChallengeStatus.EXPIRED) && currentDate.after(challengeDetails.getEndDate())){
                           log.info("challenge "+challengeDetails.getId()+" is expired");
                           challengeService.finishChallenge(challengeDetails.getId());
                       }else if(!challengeDetails.getStatus().equals(ChallengeStatus.LIVE)
                               && currentDate.after(challengeDetails.getStartDate()) && currentDate.before(challengeDetails.getEndDate())){
                           log.info("starting challenge "+challengeDetails.getId());
                           challengeService.startChallenge(challengeDetails.getId());
                       }
                   }
               }
        }
        log.debug("updateChallengeStatus cron completed "+ DateUtility.currentDate());
    }

    @Scheduled(cron = "${judge0.challenge-instance-status.updater.cron.expression}")
    public void updateChallengeInstanceStatus() throws BusinessException {
        Date currentDate = DateUtility.currentDate();
        log.debug("updateChallengeInstanceStatus cron is running "+ currentDate);
        List<ChallengeDetails> challengeDetailsList = this.challengeService.getChallenges();
        if(challengeDetailsList!=null && !challengeDetailsList.isEmpty()){
            for(ChallengeDetails challengeDetails : challengeDetailsList){
                List<ChallengeInstance> challengeInstanceList = this.challengeInstanceService.getChallengeInstanceList(challengeDetails.getId());
                if(challengeInstanceList!=null && !challengeInstanceList.isEmpty()){
                    for(ChallengeInstance challengeInstance : challengeInstanceList){
                        if(!challengeInstance.getInstanceStatus().equals(ChallengeInstanceStatus.EXPIRED) && currentDate.after(challengeInstance.getEndDate())){
                            log.info("challenge instance "+challengeInstance.getId()+" is expired");
                            this.challengeInstanceService.finishChallengeInstance(challengeInstance);
                        } else if (challengeDetails.getStatus().equals(ChallengeStatus.LIVE)
                                && !challengeInstance.getInstanceStatus().equals(ChallengeInstanceStatus.LIVE)
                                && currentDate.after(challengeInstance.getStartDate()) && currentDate.before(challengeInstance.getEndDate())) {
                            log.info("starting challenge instance " + challengeInstance.getId());
                            this.challengeInstanceService.startChallengeInstance(challengeInstance.getId());
                        }
                    }
                }
            }
        }
        log.debug("updateChallengeInstanceStatus cron completed "+ DateUtility.currentDate());
    }

}
