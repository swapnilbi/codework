import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ChallengeSubscriptionStatus } from 'src/app/challenge/model/challenge-subscription.modal';
import { Challenge, ChallengeStatus } from 'src/app/challenge/model/challenge.model';

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

  isRegistered(challenge : Challenge){    
    return this.challenge && this.challenge.challengeSubscription;
  }

}
