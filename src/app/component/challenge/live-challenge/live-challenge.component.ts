import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DiffEditorModel } from 'ngx-monaco-editor';
import { Challenge, ChallengeStatus, ParticipationStatus } from 'src/app/model/challenge.model';
import { Problem } from 'src/app/model/problem.model';
import { AlertService } from '../../common/alert/alert-service.service';
import { LoaderService } from '../../common/loader/loader.service';
import { ChallengeService } from '../view-challenge/challenge.service';
import { LiveChallengeService } from './live-challenge.service';

@Component({
  selector: 'app-live-challenge',
  templateUrl: './live-challenge.component.html',
  styleUrls: ['./live-challenge.component.scss']
})
export class LiveChallengeComponent implements OnInit {

  selectedProblem? : Problem;  
  showInstruction: boolean = false;
  showLiveChallenge: boolean = false;
  public challenge?: Challenge;    

  problems:Problem[] = []; 

  constructor(private router: Router, private route: ActivatedRoute, private challengeService: ChallengeService,private liveChallengeService: LiveChallengeService, protected alertService: AlertService, private loaderService: LoaderService) {
    
  }

  ngOnInit(): void {
    let challengeId : any = this.route.snapshot.paramMap.get('id');    
    this.loaderService.show();
    this.challengeService.getChallengeDetails(challengeId).subscribe(response => {
      this.loaderService.hide();
      this.challenge = response;
      if(!this.challenge.isRegistered){
        this.alertService.warn('Sorry! You are not registered for this challenge. Please register and try again');  
      }else if(this.challenge.status == ChallengeStatus.SCHEDULED){
        this.alertService.warn('This Challenge is not started yet!');
      }else if(this.challenge.status == ChallengeStatus.EXPIRED){
        this.alertService.warn('Sorry! This Challenge is expired');
      }else if(this.challenge.participationStatus == ParticipationStatus.FINISHED){
        this.alertService.warn('Sorry! You have already attempted this challenge');
      }else if(this.challenge.participationStatus == ParticipationStatus.NOT_STARTED){  
        this.showInstruction = true;
      }else if(this.challenge.participationStatus == ParticipationStatus.STARTED){  
        this.startChallenge();
      }
    }, error => {
      this.loaderService.hide();       
    });   
  }

  startChallenge(){    
    if(this.challenge){      
      this.loaderService.show();
      this.liveChallengeService.getProblems(this.challenge.id).subscribe(response => {
        this.problems = response;
        this.selectedProblem = this.problems[0];
        this.showInstruction = false;
        this.showLiveChallenge = true;
        this.loaderService.hide();        
      }, error => {
        this.loaderService.hide();       
      });
    }    
  }

}
