import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Utility } from 'src/app/common/utility/utility';
import { Challenge } from 'src/app/challenge/model/challenge.model';
import { LiveChallenge } from 'src/app/challenge/model/live-challenge.model';
import { ProblemSolutionResult } from 'src/app/challenge/model/problem-solution-result.model';
import { ProblemSolution } from 'src/app/challenge/model/problem-solution.model';
import { Language, Problem } from 'src/app/challenge/model/problem.model';
import { UserSubmission } from 'src/app/challenge/model/user-submission.model';
import Swal from 'sweetalert2';
import { AlertService } from '../../../../common/component/common/alert/alert-service.service';
import { LoaderService } from '../../../../common/component/common/loader/loader.service';
import { CompileResultComponent } from '../live-challenge/compile-result/compile-result.component';
import { CustomInputComponent } from '../live-challenge/custom-input/custom-input.component';
import { LiveChallengeService } from '../../../service/live-challenge.service';
import { TestResultComponent } from '../live-challenge/test-result/test-result.component';
import { ChallengeService } from '../../../service/challenge.service';
import { ChallengeInstance } from 'src/app/challenge/model/challenge-instance.model';

@Component({
  selector: 'code-editor',
  templateUrl: './code-editor.component.html',
  styleUrls: ['./code-editor.component.scss']
})
export class CodeEditorComponent implements OnInit {

  editorOptions = {
    language: 'java'
  };  

  loader = true;
  code: string= '';
  language?: Language;
  challenge?:Challenge;
  customInput? : string | null;
  public challengeInstance?: ChallengeInstance;
  userSubmission?: UserSubmission;
  runAllTestsResult?: ProblemSolutionResult;  
  compileResultModalRef!: BsModalRef;   
  customInputModalRef!: BsModalRef;   
  problemSolutionResult?: ProblemSolutionResult; 
  lastSelectedLanguage?: Language;  
  lastSavedSolution? : ProblemSolution;
  selectedProblem?: Problem;
  showEditor: boolean = true;
  editor : any;
  @Output() saveSolutionEvent = new EventEmitter<boolean>();
  @Output() finishChallengeEvent = new EventEmitter<ProblemSolution>();

  constructor(protected alertService: AlertService,private modalService: BsModalService, private loaderService: LoaderService, private challengeService: ChallengeService,private liveChallengeService: LiveChallengeService) {
    
  }
  ngOnInit(): void {
    
  }

  onEditorInit(editor : any) {
    this.editor = editor;
    let counter = 0;
    let codeLength = 0;
    let saveSolutionEvent = this.saveSolutionEvent;
    if(editor){
      editor.onKeyUp(function (e : any) {
        counter++;
        if(counter % 30 == 0){
          console.log('Save solution');
          saveSolutionEvent.emit(false);
        }
      });
    }
  }

  finishChallenge() {
    if(this.challenge && this.selectedProblem){
      let challengeSolution: ProblemSolution = {
        challengeId : this.challenge?.id,
        challengeInstanceId : this.challengeInstance?.id,
        languageId : this.getLanguage()?.id,
        problemId : this.selectedProblem.id,          
        solution : unescape(this.getSolution())
    }
      this.finishChallengeEvent.emit(challengeSolution);
    }    
  }

  @Input() set liveChallenge(liveChallenge: LiveChallenge) {   
    this.challenge = liveChallenge.challengeDetails;
    this.challengeInstance = liveChallenge.challengeInstance;
    this.userSubmission = liveChallenge.challengeInstanceSubmission;
  }

  @Input() set problem(problem: Problem) {      
    this.selectedProblem  = problem;
    this.lastSelectedLanguage = undefined;
    if(this.selectedProblem){
      if(this.selectedProblem.problemSolution && this.selectedProblem.problemSolution.languageId){
        if(this.selectedProblem.problemSolution && this.selectedProblem.problemSolution.solution){
          if(!this.selectedProblem.placeHolderSolution){
            this.selectedProblem.placeHolderSolution = {};
          } 
          this.selectedProblem.placeHolderSolution[this.selectedProblem.problemSolution.languageId] = this.selectedProblem.problemSolution.solution;
        }
        let selectedLanguage = this.selectedProblem.languagesAllowed?.find( l => this.selectedProblem && l.id ===  this.selectedProblem.problemSolution?.languageId);
        if(selectedLanguage){
          this.onLanguageChange(selectedLanguage);
        }
      } 
      if(!this.language && this.selectedProblem.languagesAllowed){
        this.onLanguageChange(this.selectedProblem.languagesAllowed[0]);
      }      
    }
  }

  updateSolution(language : Language){
    console.log(this.selectedProblem);
    if(this.selectedProblem){  
      if(this.selectedProblem.placeHolderSolution && this.selectedProblem.placeHolderSolution[language.id]){
        this.code = this.selectedProblem.placeHolderSolution[language.id];        
      }else{
        this.code = '';        
      }
    } 
  }

  onLanguageChange(language? : Language){    
    if(language){

      if(this.lastSelectedLanguage && this.selectedProblem && this.selectedProblem.placeHolderSolution){
        this.selectedProblem.placeHolderSolution[this.lastSelectedLanguage.id] = this.code; 
      }
      this.lastSelectedLanguage = language;  
      this.language =  language;
      this.updateSolution(language);    
      this.editorOptions.language = language.editorCode;              
      this.showEditor = false;    
      setTimeout(() =>{
        this.showEditor = true;      
      });
    }    
  }

  getSolution(){
    return this.code;
  }

  getLanguage(){
    return this.language;
  }

  getProblemSolution(){
    if(this.challenge && this.selectedProblem){   
      let challengeSolution: ProblemSolution = {
          challengeId : this.challenge.id,
          challengeInstanceId : this.challengeInstance?.id,
          languageId : this.getLanguage()?.id,
          problemId : this.selectedProblem.id,
          customInput : this.customInput,
          solution : unescape(this.getSolution())
      }
      return challengeSolution;
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

  saveSolution(showMessage : boolean){    
    let challengeSolution = this.getProblemSolution();
    if(challengeSolution && ( Utility.getHashCode(JSON.stringify(challengeSolution)) != Utility.getHashCode(JSON.stringify(this.lastSavedSolution)))){                         
      this.lastSavedSolution = Object.assign({}, challengeSolution);
      this.liveChallengeService.saveProblemSolution(challengeSolution).subscribe(response => {    
        if(response){
          if(showMessage){
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Your solution has been saved',
              showConfirmButton: false,
              timer: 2000
            })
          }
        }                
      }, error => {
        this.loaderService.hide();       
      });      
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

}
