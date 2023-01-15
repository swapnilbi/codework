import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { EvaluateProblem } from 'src/app/challenge/model/evaluate-problem.model';
import { ChallengInstanceService } from '../../../../../service/challenge-instance.service';
import { LoaderService } from 'src/app/common/component/common/loader/loader.service';
import { AlertService } from 'src/app/common/component/common/alert/alert-service.service';
import { ProblemSolution } from 'src/app/challenge/model/problem-solution.model';
import { TimeTakenPipe } from 'src/app/challenge/pipe/time-taken-pipe';

@Component({
  selector: 'app-view-submission',
  templateUrl: './view-submission.component.html'
})
export class ViewSubmissionComponent implements OnInit {

  selectedProblem? : EvaluateProblem;
  evaluationForm: FormGroup;
  viewMode : boolean = false;
  submittedProblems? : Array<EvaluateProblem>;
  evaluationStatusList? : Array<string> = ["COMPLETED","IN_PROGRESS"];
  resultList? : Array<string> = ["PASS","FAIL"];
  editorOptions = {
    language: 'java',
    readOnly: true
  };  
  code? : string;

  constructor(private _bsModalRef: BsModalRef,
    private loaderService : LoaderService,
    private timeTakenPipe: TimeTakenPipe,
    private alertService : AlertService,
    private challengeInstanceService : ChallengInstanceService,
    private fb:FormBuilder) { 
    
    this.evaluationForm = this.fb.group({
      "id" : new FormControl("", Validators.required),   
      "name": new FormControl("", Validators.required),   
      "language": new FormControl(""),
      "type": new FormControl("", Validators.required),   
      "timeTaken": new FormControl("", Validators.required),               
      "solution": new FormControl(null),
      "avgExecutionTime": new FormControl(null),        
      "evaluationStatus": new FormControl(null, Validators.required),                
      "solutionResult": new FormControl(null, Validators.required),
      "points": new FormControl(null),    
      "evaluationRemarks": new FormControl(null)                            
    });   
  }

  onProblemChange(e : any) {
    if(this.selectedProblem){
      this.updateForm(this.selectedProblem);
    }    
  }

  evaluateChallenge(evaluateForm : any): void {    
    this.loaderService.show();         
    let evaluateInput : ProblemSolution = {
      id : evaluateForm.id,
      evaluationStatus : evaluateForm.evaluationStatus,
      evaluationRemarks : evaluateForm.evaluationRemarks,
      solutionResult : evaluateForm.solutionResult,
      points : evaluateForm.points
    }
    this.challengeInstanceService.updateProblemSolution(evaluateInput).subscribe(response => {    
      if(response){         
          this.loaderService.hide();       
          this.alertService.success("Submission has been updated successfully");          
        }                
      }, error => {
        this.loaderService.hide();       
    }); 
  }

  resetSubmission(selectedProblem : EvaluateProblem): void {    
    this.loaderService.show();             
    if(selectedProblem && selectedProblem.problemSolution && selectedProblem.problemSolution.id){
      this.challengeInstanceService.resetProblemSolution(selectedProblem.problemSolution.id).subscribe(response => {    
        if(response){         
            this.loaderService.hide();       
            this.alertService.success("Submission has been deleted successfully");     
            this.onCancel();     
          }                
        }, error => {
          this.loaderService.hide();       
      }); 
    }    
  }

  updateForm(selectedProblem : EvaluateProblem){     
    if(selectedProblem.language){
      this.editorOptions.language = selectedProblem.language.editorCode;              
    }              
    if(selectedProblem.problemSolution.avgExecutionTime){
       selectedProblem.problemSolution.avgExecutionTime = parseFloat(selectedProblem.problemSolution.avgExecutionTime.toFixed(2));
    }     
    let challengeForm = {    
      "id" :   selectedProblem.problemSolution.id,
      "name": selectedProblem.name,
      "language": selectedProblem.language?.name,
      "type": selectedProblem.type,
      "timeTaken": this.timeTakenPipe.transform(selectedProblem.problemSolution.timeTaken),
      "solution": selectedProblem.problemSolution.solution,
      "avgExecutionTime": selectedProblem.problemSolution.avgExecutionTime,
      "evaluationStatus": selectedProblem.problemSolution.evaluationStatus,
      "solutionResult": selectedProblem.problemSolution.solutionResult,
      "points": selectedProblem.problemSolution.points,
      "evaluationRemarks": selectedProblem.problemSolution.evaluationRemarks,
    }                  
    this.evaluationForm.patchValue(challengeForm);
  }

  ngOnInit(): void {
    if(this.submittedProblems && this.submittedProblems.length > 0){
      this.selectedProblem = this.submittedProblems[0];      
      this.updateForm(this.selectedProblem);      
    }    
  }

  public onCancel(): void {     
    this._bsModalRef.hide();    
  }

}
