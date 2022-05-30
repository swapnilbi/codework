import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from 'src/app/component/common/alert/alert-service.service';
import { LoaderService } from 'src/app/component/common/loader/loader.service';
import { ChallengeInstance } from 'src/app/model/challenge-instance.model';
import { ChallengeService } from '../../../../service/challenge.service';

@Component({
  selector: 'app-manage-challenge-instance',
  templateUrl: './manage-challenge-instance.component.html',
  styleUrls: ['./manage-challenge-instance.component.scss']
})
export class ManageChallengeInstanceComponent implements OnInit {

  challengInstanceList : Array<ChallengeInstance>;

  constructor(private challengeService : ChallengeService, 
    private route: ActivatedRoute, 
    private location : Location,
    private router: Router, 
    private alertService : AlertService, 
    private loaderService: LoaderService) {
    this.challengInstanceList = [];
  }

  ngOnInit(): void {
    let challengeId : any = this.route.snapshot.paramMap.get('id');    
    this.challengeService.getChallengeInstanceList(challengeId).subscribe(response => {
      this.challengInstanceList = response;
    })
  }

  stopChallengeInstance(instance : ChallengeInstance){
    this.loaderService.show();       
    this.challengeService.stopChallengeInstance(instance.id).subscribe(response => {    
      if(response){ 
        instance.instanceStatus = response.instanceStatus;               
        this.loaderService.hide();       
        this.alertService.success('Challenge instance stopped successfully');
        }                
      }, error => {
        this.loaderService.hide();       
      }); 
  }

  startChallengeInstance(instance : ChallengeInstance){
    this.loaderService.show();       
    this.challengeService.startChallengeInstance(instance.id).subscribe(response => {    
      if(response){ 
        instance.instanceStatus = response.instanceStatus;               
        this.loaderService.hide();       
        this.alertService.success('Challenge instance started successfully');
        }                
      }, error => {
        this.loaderService.hide();       
      }); 
  }

  manageProblems(instance : ChallengeInstance){    
    var url = 'challenge/instance/'+instance.id+'/problem/manage';
    this.router.navigateByUrl(url);    
  }

  back(){        
    this.location.back();
  }

}

