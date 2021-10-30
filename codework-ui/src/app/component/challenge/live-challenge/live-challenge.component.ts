import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Challenge, ChallengeStatus, ParticipationStatus } from 'src/app/model/challenge.model';
import { Language, Problem } from 'src/app/model/problem.model';
import { ProblemSolution } from 'src/app/model/problem-solution.model';
import { AlertService } from '../../common/alert/alert-service.service';
import { LoaderService } from '../../common/loader/loader.service';
import { CodeEditorComponent } from '../code-editor/code-editor.component';
import { ChallengeService } from '../view-challenge/challenge.service';
import { LiveChallengeService } from './live-challenge.service';
import { ProblemSolutionResult } from 'src/app/model/problem-solution-result.model';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { CompileResultComponent } from './compile-result/compile-result.component';
import { CustomInputComponent } from './custom-input/custom-input.component';
import { TestResultComponent } from './test-result/test-result.component';
import Swal from 'sweetalert2/dist/sweetalert2.js';
import { ChallengeSubscriptionStatus } from 'src/app/model/challenge-subscription.modal';
import { Utility } from 'src/app/common/utility/utility';
import { ChallengeSubmitInput } from 'src/app/model/challenge-submit.model';

@Component({
  selector: 'app-live-challenge',
  templateUrl: './live-challenge.component.html',
  styleUrls: ['./live-challenge.component.scss']
})
export class LiveChallengeComponent implements OnInit {

  selectedProblem? : Problem;  
  customInput? : string | null;
  showInstruction: boolean = false;
  showLiveChallenge: boolean = false;
  public challenge?: Challenge;   
  lastSavedSolution? : ProblemSolution;
  problemSolutionResult?: ProblemSolutionResult; 
  runAllTestsResult?: ProblemSolutionResult;  
  compileResultModalRef!: BsModalRef;   
  customInputModalRef!: BsModalRef;   
  language?: Language;
  @ViewChild(CodeEditorComponent) codeEditor!: CodeEditorComponent;

  problems:Problem[] = []; 

  constructor(private router: Router,private modalService: BsModalService, private route: ActivatedRoute, private challengeService: ChallengeService,private liveChallengeService: LiveChallengeService, protected alertService: AlertService, private loaderService: LoaderService) {
    
  }

  ngOnInit(): void {    
    let challengeId : any = this.route.snapshot.paramMap.get('id');    
    this.loaderService.show();
    this.challengeService.getChallengeDetails(challengeId).subscribe(response => {
      this.loaderService.hide();
      this.challenge = response;
      if(!this.challenge.challengeSubscription){
        this.alertService.warn('Sorry! You are not registered for this challenge. Please register and try again');  
        this.nevigateToChallenges();
      }else if(this.challenge.status == ChallengeStatus.SCHEDULED){
        this.alertService.warn('This Challenge is not started yet!');
        this.nevigateToChallenges();
      }else if(this.challenge.status == ChallengeStatus.EXPIRED){
        this.alertService.info('Sorry! This Challenge is expired');
        this.nevigateToChallenges();
      }else if(this.challenge.challengeSubscription.status == ChallengeSubscriptionStatus.SUBMITTED){
        this.alertService.warn('Sorry! You have already attempted this challenge');
        this.nevigateToChallenges();
      }else if(this.challenge.challengeSubscription.status == ChallengeSubscriptionStatus.REGISTERED){  
        this.showInstruction = true;
      }else if(this.challenge.challengeSubscription.status == ChallengeSubscriptionStatus.STARTED){  
        this.resumeChallenge();
      }
    }, error => {
      this.loaderService.hide();       
    });   
  }

  nevigateToChallenges(){
    this.router.navigate(['challenges']);    
  }

  startChallenge(){    
    if(this.challenge){      
      this.loaderService.show();
      this.challengeService.startChallenge(this.challenge.id).subscribe(response => {
        this.challenge = response;
        this.loaderService.hide(); 
        if(response.challengeSubscription?.status ==  ChallengeSubscriptionStatus.STARTED){
          this.resumeChallenge();
        }       
      }, error => {
        this.loaderService.hide();       
      });
    }    
  }

  resumeChallenge(){    
    if(this.challenge){      
      this.loaderService.show();
      this.liveChallengeService.getProblems(this.challenge.id).subscribe(response => {
        this.problems = response;        
        this.selectedProblem = this.problems[0];
        if(this.selectedProblem && this.selectedProblem.languagesAllowed){
          this.language = this.selectedProblem.languagesAllowed[0];
        }                
        this.showInstruction = false;
        this.showLiveChallenge = true;
        this.loaderService.hide();        
      }, error => {
        this.loaderService.hide();       
      });
    }    
  }

  getProblemSolution(){
    if(this.challenge && this.selectedProblem){   
      let challengeSolution: ProblemSolution = {
          challengeId : this.challenge.id,
          languageId : this.codeEditor.getLanguage()?.id,
          problemId : this.selectedProblem.id,
          customInput : this.customInput,
          solution : unescape(this.codeEditor.getSolution())
      }
      return challengeSolution;
    }
    return null;
  }

  getAllProblemSolutions(){
    if(this.challenge && this.selectedProblem){   
      let challengeSolution: ProblemSolution = {
          challengeId : this.challenge.id,
          languageId : this.codeEditor.getLanguage()?.id,
          problemId : this.selectedProblem.id,
          customInput : this.customInput,
          solution : unescape(this.codeEditor.getSolution())
      }
      let solutions : ChallengeSubmitInput = {
        challengeId : this.challenge.id,
        solutions : [challengeSolution]
      }
      return solutions;
    }
    return null;
  }

  compileSolution(){    
    this.saveSolution(false);
    let challengeSolution = this.getProblemSolution();
    if(challengeSolution){         
      this.loaderService.show();
      this.liveChallengeService.compileSolution(challengeSolution).subscribe(response => {    
        this.problemSolutionResult = response;
        this.customInput = null;
        this.showCompilationResult();
        this.loaderService.hide();        
      }, error => {
        this.loaderService.hide();       
      });      
    }    
  }

  runAllTests(){
    this.saveSolution(false);    
    let challengeSolution = this.getProblemSolution();
    if(challengeSolution){                   
      this.loaderService.show();
      this.liveChallengeService.runAllTests(challengeSolution).subscribe(response => {    
        this.runAllTestsResult = response;
        this.customInput = null;
        this.showAllTestsResult();
        this.loaderService.hide();        
      }, error => {
        this.loaderService.hide();       
      });      
    }    
  }

  showAllTestsResult() {
    if(this.runAllTestsResult){
      const initialState = {
        compileResult : this.runAllTestsResult
      };
      this.compileResultModalRef = this.modalService.show(TestResultComponent, {initialState});  
    }
  }

  showCompilationResult(){
    if(this.problemSolutionResult){
      const initialState = {
        compileResult : this.problemSolutionResult
      };
      this.compileResultModalRef = this.modalService.show(CompileResultComponent, {initialState});  
    }    
  }

  showTestResult(){        
    this.showCompilationResult();
  }

  provideCustomInput(){                
    const initialState = {
      input : this.selectedProblem?.testCases ? this.selectedProblem?.testCases[0].input: ''
    };
    this.customInputModalRef = this.modalService.show(CustomInputComponent, {initialState});
    this.customInputModalRef.content.onSubmit.subscribe((result : any) => {
       this.customInput = result;
       this.compileSolution();
    })
  }

  saveSolution(showMessage : boolean){    
    let challengeSolution = this.getProblemSolution();
    if(challengeSolution && ( Utility.getHashCode(JSON.stringify(challengeSolution)) != Utility.getHashCode(JSON.stringify(this.lastSavedSolution)))){                         
      this.lastSavedSolution = Object.assign({}, challengeSolution);
      this.liveChallengeService.saveProblemSolution(challengeSolution).subscribe(response => {    
        if(response && showMessage){
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Your solution has been saved',
            showConfirmButton: false,
            timer: 2000
          })
        }                
      }, error => {
        this.loaderService.hide();       
      });      
    }    
  }

  
  submitSolution(){        
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
        let challengeSolution = this.getProblemSolution();
        if(challengeSolution){
          this.loaderService.show();
          this.liveChallengeService.submitProblemSolution(challengeSolution).subscribe(response => {    
          if(response){
            if(this.selectedProblem){
              this.selectedProblem.problemSolution = response;
            }
            this.loaderService.hide();       
            this.alertService.success('Your Solution has been submitted successfully');
            }                
          }, error => {
            this.loaderService.hide();       
          });      
        }
      }    
   })
 }

  isSolutionSubmitted(problem? : Problem){
    return problem && problem.problemSolution && problem.problemSolution.submitted;
  }
 
  finishChallenge(){        
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
        let challengeSolution = this.getAllProblemSolutions();
        if(challengeSolution){
          this.loaderService.show();
          this.liveChallengeService.finishChallenge(challengeSolution).subscribe(response => {    
            this.loaderService.hide();       
            this.nevigateToChallenges();
          }, error => {
            this.loaderService.hide();       
          });      
        }
      }
    })
  }

}

