import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { EvaluateProblem } from 'src/app/challenge/model/evaluate-problem.model';
import { ChallengInstanceService } from '../../../../../service/challenge-instance.service';
import { UserSubmission } from 'src/app/challenge/model/user-submission.model';
import { LoaderService } from 'src/app/common/component/common/loader/loader.service';
import { AlertService } from 'src/app/common/component/common/alert/alert-service.service';
import { ProblemSolution } from 'src/app/challenge/model/problem-solution.model';

@Component({
  selector: 'app-view-submission',
  templateUrl: './view-submission.component.html'
})
export class ViewSubmissionComponent implements OnInit {

  selectedProblem? : EvaluateProblem;
  evaluationForm: FormGroup;
  submittedProblems? : Array<EvaluateProblem>;
  evaluationStatusList? : Array<string> = ["NOT_STARTED","COMPLETED","IN_PROGRESS"];
  resultList? : Array<string> = ["PASS","FAIL"];
  editorOptions = {
    language: 'java',
    readOnly: true
  };  
  code? : string;

  constructor(private _bsModalRef: BsModalRef,
    private loaderService : LoaderService,
    private alertService : AlertService,
    private challengeInstanceService : ChallengInstanceService,
    private fb:FormBuilder) { 
    
    this.evaluationForm = this.fb.group({
      "id" : new FormControl("", Validators.required),   
      "name": new FormControl("", Validators.required),   
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
    this.challengeInstanceService.updateProblemSolution(evaluateForm).subscribe(response => {    
      if(response){         
          this.loaderService.hide();       
          this.alertService.success("Submission has been updated successfully");          
        }                
      }, error => {
        this.loaderService.hide();       
    }); 
  }

  updateForm(selectedProblem : EvaluateProblem){
     let timeTaken : number = 0;
     if(selectedProblem.language){
      this.editorOptions.language = selectedProblem.language.editorCode;              
     }     
     if(selectedProblem.problemSolution.timeTaken){
        timeTaken = (selectedProblem.problemSolution.timeTaken / 1000)/60;        
     }
    let challengeForm = {    
      "id" :   selectedProblem.problemSolution.id,
      "name": selectedProblem.name,
      "type": selectedProblem.type,
      "timeTaken": timeTaken,
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
