import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BsModalService } from 'ngx-bootstrap/modal';
import { UserSubmission } from 'src/app/challenge/model/user-submission.model';
import { ChallengInstanceService } from 'src/app/challenge/service/challenge-instance.service';
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
    private loaderService: LoaderService) { }

  public submissionList: Array<UserSubmission> = [];

  ngOnInit(): void {    
    this.instanceId = this.route.snapshot.paramMap.get('instanceId');    
    this.loaderService.show();
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
      this.modalService.show(ViewSubmissionComponent, {initialState, class: 'modal-xl modal-dialog-scrollable'});              
    }, error => {
      this.loaderService.hide();
    }
    );    
  }

  back(){        
    this.location.back();
  }

}
