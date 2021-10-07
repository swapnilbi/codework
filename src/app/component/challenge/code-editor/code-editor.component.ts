import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { Language, Problem } from 'src/app/model/problem.model';
import { AlertService } from '../../common/alert/alert-service.service';
import { LoaderService } from '../../common/loader/loader.service';

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
  selectedProblem?: Problem;
  showEditor: boolean = true;
  @Output() saveSolutionEvent = new EventEmitter<boolean>();
  @Output() finishChallengeEvent = new EventEmitter<boolean>();

  saveSolution() {
    this.saveSolutionEvent.emit(true);
  }

  finishChallenge() {
    this.finishChallengeEvent.emit(true);
  }

  constructor(protected alertService: AlertService) {
    
  }

  ngOnInit(): void {    
  }

  @Input() set problem(problem: Problem) {      
    this.selectedProblem  = problem;
    this.updateSolution();    
  }

  updateSolution(){        
    if(this.selectedProblem){
      if(this.selectedProblem.solution && this.selectedProblem.solution.trim().length > 0){
        this.code = this.selectedProblem.solution;
      }else if(this.selectedProblem.placeHolderSolution){
        this.code = this.selectedProblem.placeHolderSolution;
      } 
      if(this.selectedProblem.languagesAllowed){
        this.language = this.selectedProblem.languagesAllowed[0];  
        this.onLanguageChange(this.language);
      }      
    }
  }

  onLanguageChange(language? : Language){    
    if(language){
      this.language = language;
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

}
