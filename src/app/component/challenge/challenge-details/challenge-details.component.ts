import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Challenge, ChallengeStatus } from 'src/app/model/challenge.model';
import { AlertService } from '../../common/alert/alert-service.service';
import { LoaderService } from '../../common/loader/loader.service';
import { ChallengeService } from '../view-challenge/challenge.service';

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
        console.log('registered');
        this.alertService.success("You have been successfully registered");        
        this.challenge = response;          
        this.loaderService.hide();
      }, error => {
        this.loaderService.hide();         
      });
    }    
  }

  isLiveChallenge(){
    if(this.challenge){
      return this.challenge.status ==  ChallengeStatus.LIVE;
    }
    return false;
  }

  startChallenge(){    
    if(this.challenge){
      var url = '/challenge/'+this.challenge.id+'/live';
      this.router.navigateByUrl(url);    
    }    
  }

}
