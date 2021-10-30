import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ChallengeSubscriptionStatus } from 'src/app/model/challenge-subscription.modal';
import { Challenge, ChallengeStatus } from 'src/app/model/challenge.model';

@Component({
  selector: 'challenge-widget',
  templateUrl: './challenge-widget.component.html',
  styleUrls: ['./challenge-widget.component.scss']
})
export class ChallengeWidgetComponent implements OnInit {


  @Input() challenge!: Challenge;

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  viewChallenge(){
    var url = '/challenge/'+this.challenge.id+'/details';
    this.router.navigateByUrl(url);
  }

  isChallengeSubmitted(){
    return this.challenge && this.challenge.challengeSubscription && this.challenge.challengeSubscription.status ==  ChallengeSubscriptionStatus.SUBMITTED;
  }

  isChallengeStarted(){
    return this.challenge && this.challenge.challengeSubscription && this.challenge.challengeSubscription.status ==  ChallengeSubscriptionStatus.STARTED;
  }

  isLiveChallenge(){
    if(this.challenge && this.challenge.status ==  ChallengeStatus.LIVE){
      if(this.challenge.challengeSubscription && this.challenge.challengeSubscription.status ==  ChallengeSubscriptionStatus.SUBMITTED){
        return false;
      }
      return true;
    }
    return false;
  }

  isRegistered(challenge : Challenge){
    return this.challenge && this.challenge.challengeSubscription;
  }

  startChallenge(){    
    if(this.challenge){
      var url = '/challenge/'+this.challenge.id+'/live';
      this.router.navigateByUrl(url);    
    }    
  }

}
