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
      if(!this.challenge.isRegistered){
        this.alertService.warn('Sorry! You are not registered for this challenge. Please register and try again');  
        this.nevigateToChallenges();
      }else if(this.challenge.status == ChallengeStatus.SCHEDULED){
        this.alertService.warn('This Challenge is not started yet!');
        this.nevigateToChallenges();
      }else if(this.challenge.status == ChallengeStatus.EXPIRED){
        this.alertService.info('Sorry! This Challenge is expired');
        this.nevigateToChallenges();
      }else if(this.challenge.participationStatus == ParticipationStatus.FINISHED){
        this.alertService.warn('Sorry! You have already attempted this challenge');
        this.nevigateToChallenges();
      }else if(this.challenge.participationStatus == ParticipationStatus.NOT_STARTED){  
        this.showInstruction = true;
      }else if(this.challenge.participationStatus == ParticipationStatus.STARTED){  
        this.startChallenge();
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
          solution : unescape(this.codeEditor.getSolution())
      }
      return challengeSolution;
    }
    return null;
  }

  compileSolution(){    
    let challengeSolution = this.getProblemSolution();
    if(challengeSolution){         
      this.loaderService.show();
      this.liveChallengeService.compileSolution(challengeSolution).subscribe(response => {    
        this.problemSolutionResult = response;
        this.showCompilationResult();
        this.loaderService.hide();        
      }, error => {
        this.loaderService.hide();       
      });      
    }    
  }

  runAllTests(){    
    let challengeSolution = this.getProblemSolution();
    if(challengeSolution){                   
      this.loaderService.show();
      this.liveChallengeService.runAllTests(challengeSolution).subscribe(response => {    
        this.runAllTestsResult = response;
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
      input : this.selectedProblem?.sampleTestCase?.input
    };
    this.customInputModalRef = this.modalService.show(CustomInputComponent, {initialState});
    this.customInputModalRef.content.onClose.subscribe((result : any) => {
      console.log('results', result);
      /*
        set custom input
        send while compilation
      */
    })
  }

  saveSolution(){    
    let challengeSolution = this.getProblemSolution();
    if(challengeSolution){                         
      this.loaderService.show();
      this.liveChallengeService.saveProblemSolution(challengeSolution).subscribe(response => {    
        this.loaderService.hide();        
        if(response){
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
       
     }
   })
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
        
      }
    })
  }

}

