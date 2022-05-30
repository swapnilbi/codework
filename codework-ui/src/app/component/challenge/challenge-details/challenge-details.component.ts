import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ChallengeSubscriptionStatus } from 'src/app/model/challenge-subscription.modal';
import { Challenge, ChallengeStatus } from 'src/app/model/challenge.model';
import { UserSubmission } from 'src/app/model/user-submission.model';
import { AlertService } from '../../common/alert/alert-service.service';
import { LoaderService } from '../../common/loader/loader.service';
import { ChallengeService } from '../../../service/challenge.service';

@Component({
  selector: 'app-challenge-details',
  templateUrl: './challenge-details.component.html',
  styleUrls: ['./challenge-details.component.scss']
})
export class ChallengeDetailsComponent implements OnInit {

  public challenge?: Challenge;

  constructor(private router: Router, private route: ActivatedRoute, private challengeService : ChallengeService, protected alertService: AlertService, private loaderService: LoaderService) {     
  }

  ngOnInit(): void {
    let challengeId : any = this.route.snapshot.paramMap.get('id');    
    this.loaderService.show();
    this.challengeService.getChallengeDetails(challengeId).subscribe(response => {
      this.loaderService.hide();
      this.challenge = response;
    }, error => {
      this.loaderService.hide();       
    });    
  }

  registerChallenge(){    
    if(this.challenge){
      this.loaderService.show();
      this.challengeService.registerChallenge(this.challenge?.id).subscribe(response => {
        this.challenge = response;
        if(response.challengeSubscription?.status == ChallengeSubscriptionStatus.REGISTERED){
          this.alertService.success("You have been successfully registered");    
        }              
        this.loaderService.hide();
      }, error => {
        this.loaderService.hide();         
      });
    }    
  }

  isRegistered(challenge : Challenge){
    return this.challenge && this.challenge.challengeSubscription;
  }

  isLiveChallenge(){
    return this.challenge && this.challenge.status == ChallengeStatus.LIVE && this.challenge.challengeSubscription && this.challenge.challengeSubscription.status ==  ChallengeSubscriptionStatus.REGISTERED;
  }

  startChallenge(userSubmission:  UserSubmission){    
    if(userSubmission){
      var url = '/challenge/'+userSubmission.challengeInstanceId+'/live';
      this.router.navigateByUrl(url);    
    }    
  }

}
