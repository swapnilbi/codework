import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { UserSubmission } from 'src/app/challenge/model/user-submission.model';
import { ChallengInstanceService } from 'src/app/challenge/service/challenge-instance.service';
import { AlertService } from 'src/app/common/component/common/alert/alert-service.service';
import { LoaderService } from 'src/app/common/component/common/loader/loader.service';
import { ViewSubmissionComponent } from './view-submission/view-submission.component';


@Component({
  selector: 'app-evaluate-challenge-instance',
  templateUrl: './evaluate-challenge-instance.component.html'  
})
export class EvaluateChallengeInstanceComponent implements OnInit {

  instanceId : any;

  constructor(private challengeInstanceService : ChallengInstanceService, 
    private route: ActivatedRoute,
    private modalService: BsModalService,
    private location: Location,
    private alertService : AlertService,
    private loaderService: LoaderService) { }

  public submissionList: Array<UserSubmission> = [];

  ngOnInit(): void {    
    this.instanceId = this.route.snapshot.paramMap.get('instanceId');    
    this.loaderService.show();    
    this.getChallengeInstanceSubmissions();
  }

  getChallengeInstanceSubmissions(){
    this.challengeInstanceService.getChallengeInstanceSubmissions(this.instanceId).subscribe(response => {      
      this.loaderService.hide();
      this.submissionList = response;      
    }, error => {
      this.loaderService.hide();
    }
    );
  }

  viewSubmissions(userSubmission: UserSubmission){
    this.loaderService.show();
    this.challengeInstanceService.getSubmittedProblems(userSubmission.id).subscribe(response => {      
      this.loaderService.hide();
      const initialState = {
        submittedProblems : response 
      };
      let modalRef: BsModalRef = this.modalService.show(ViewSubmissionComponent, {initialState, class: 'modal-xl modal-dialog-scrollable'});            
      if(modalRef.onHidden){
        modalRef.onHidden.subscribe((result : any) => {
          this.getChallengeInstanceSubmissions();
        })
      }      
    }, error => {
      this.loaderService.hide();
    }
    );    
  }

  checkSubmissionResult(){
    this.loaderService.show();
    this.challengeInstanceService.checkSubmissionResult(this.instanceId).subscribe(response => {      
      this.alertService.success("Evaluation is in progress")
      this.loaderService.hide();      
    }, error => {
      this.loaderService.hide();
    }
    ); 
  }

  back(){        
    this.location.back();
  }

}
