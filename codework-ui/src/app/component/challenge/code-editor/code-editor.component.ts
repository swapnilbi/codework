import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { Language, Problem } from 'src/app/model/problem.model';
import { AlertService } from '../../common/alert/alert-service.service';

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
  lastSelectedLanguage?: Language;
  selectedProblem?: Problem;
  showEditor: boolean = true;
  editor : any;
  @Output() saveSolutionEvent = new EventEmitter<boolean>();
  @Output() finishChallengeEvent = new EventEmitter<boolean>();

  constructor(protected alertService: AlertService) {
    
  }
  ngOnInit(): void {
    
  }

  saveSolution() {
    this.saveSolutionEvent.emit(true);
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

  isSolutionSubmitted(){
    return this.selectedProblem && this.selectedProblem.problemSolution && this.selectedProblem.problemSolution.submitted;
  }

  finishChallenge() {
    this.finishChallengeEvent.emit(true);
  }

  @Input() set problem(problem: Problem) {      
    this.selectedProblem  = problem;
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

}
