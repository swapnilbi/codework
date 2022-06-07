import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Challenge } from 'src/app/challenge/model/challenge.model';
import { Problem } from 'src/app/challenge/model/problem.model';
import { ProblemSolution } from 'src/app/challenge/model/problem-solution.model';
import { AlertService } from '../../../../common/component/common/alert/alert-service.service';
import { LoaderService } from '../../../../common/component/common/loader/loader.service';
import { CodeEditorComponent } from '../code-editor/code-editor.component';
import { ChallengeService } from '../../../service/challenge.service';
import { LiveChallengeService } from '../../../service/live-challenge.service';
import Swal from 'sweetalert2/dist/sweetalert2.js';
import { ChallengeSubmitInput } from 'src/app/challenge/model/challenge-submit.model';
import { ChallengeInstanceStatus } from 'src/app/challenge/model/challenge-instance.model';
import { SubmissionStatus } from 'src/app/challenge/model/user-submission.model';
import { LiveChallenge } from 'src/app/challenge/model/live-challenge.model';

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
  public liveChallenge?: LiveChallenge;     
  @ViewChild(CodeEditorComponent) codeEditor!: CodeEditorComponent;

  problems:Problem[] = []; 

  constructor(private router: Router,    
    private route: ActivatedRoute,     
    private challengeService: ChallengeService,
    private liveChallengeService: LiveChallengeService, 
    protected alertService: AlertService, 
    private loaderService: LoaderService) {
    
  }

  ngOnInit(): void {    
    let challengeInstanceId : any = this.route.snapshot.paramMap.get('id');    
    this.loaderService.show();
    this.challengeService.getLiveChallengeDetails(challengeInstanceId).subscribe(response => {
      this.loaderService.hide();
      this.liveChallenge = response     
      if(this.liveChallenge){
        this.challenge = this.liveChallenge.challengeDetails;
        if(!this.challenge.challengeSubscription){
          this.alertService.warn('Sorry! You are not registered for this challenge. Please register and try again');  
          this.nevigateToChallenges();
        }else if(this.liveChallenge.challengeInstance.instanceStatus == ChallengeInstanceStatus.CREATED){
          this.alertService.warn('This Challenge is not started yet!');
          this.nevigateToChallenges();
        }else if(this.liveChallenge.challengeInstance.instanceStatus == ChallengeInstanceStatus.EXPIRED){
          this.alertService.info('Sorry! This Challenge is expired');
          this.nevigateToChallenges();
        }else if(this.liveChallenge.challengeInstanceSubmission && this.liveChallenge.challengeInstanceSubmission.submissionStatus == SubmissionStatus.SUBMITTED){
          this.alertService.warn('Sorry! You have already attempted this challenge');
          this.nevigateToChallenges();
        }else if(!this.liveChallenge.challengeInstanceSubmission || this.liveChallenge.challengeInstanceSubmission.submissionStatus == SubmissionStatus.NOT_STARTED){  
          this.showInstruction = true;
        }else if(this.liveChallenge.challengeInstanceSubmission && this.liveChallenge.challengeInstanceSubmission.submissionStatus == SubmissionStatus.IN_PROGRESS){  
          this.resumeChallenge();
        }
      }      
    }, error => {
      this.loaderService.hide();       
    });   
  }

  nevigateToChallenges(){
    this.router.navigate(['challenges']);    
  }

  startChallenge(){    
    if(this.liveChallenge && this.liveChallenge.challengeInstance){      
      this.loaderService.show();
      this.liveChallengeService.startChallenge(this.liveChallenge.challengeInstance.id).subscribe(response => {
        this.liveChallenge = response;
        this.challenge = response.challengeDetails;        
        this.loaderService.hide();         
        this.resumeChallenge();        
      }, error => {
        this.loaderService.hide();       
      });
    }    
  }

  resumeChallenge(){    
    if(this.liveChallenge && this.liveChallenge.challengeInstance){      
      this.loaderService.show();
      this.liveChallengeService.getProblems(this.liveChallenge.challengeInstance.id).subscribe(response => {
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

  getAllProblemSolutions(){
    if(this.challenge && this.selectedProblem){   
      let challengeSolution: ProblemSolution = {
          challengeId : this.challenge.id,
          challengeInstanceId : this.liveChallenge?.challengeInstance?.id,
          languageId : this.codeEditor.getLanguage()?.id,
          problemId : this.selectedProblem.id,          
          solution : unescape(this.codeEditor.getSolution())
      }
      let solutions : ChallengeSubmitInput = {
        challengeInstanceId : this.liveChallenge?.challengeInstance?.id,
        solutionList : [challengeSolution]
      }
      return solutions;
    }
    return null;
  }

  hasMultipleProblems(problem? : Problem){
    return this.problems && this.problems.length > 1;
  }
 
  finishChallenge(solution : ProblemSolution){        
     Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes'
    }).then((result) => {
      if (result.isConfirmed) {        
        let challengeSolution : ChallengeSubmitInput = {
          challengeInstanceId : this.liveChallenge?.challengeInstance?.id,
          solutionList : [solution]
        }        
        console.log(challengeSolution);
        if(challengeSolution){          
          this.loaderService.show();
          this.liveChallengeService.finishChallenge(challengeSolution).subscribe(response => {    
            this.loaderService.hide();       
            this.alertService.success('Your Solution has been submitted successfully');
            this.nevigateToChallenges();
          }, error => {
            this.loaderService.hide();       
          });              
        }
      }
    })
  }

}

