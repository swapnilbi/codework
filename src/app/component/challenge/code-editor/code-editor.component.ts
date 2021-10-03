import { Component, Input, OnInit } from '@angular/core';
import { Language, Problem } from 'src/app/model/problem.model';

@Component({
  selector: 'code-editor',
  templateUrl: './code-editor.component.html',
  styleUrls: ['./code-editor.component.scss']
})
export class CodeEditorComponent implements OnInit {

  editorOptions = {
    language: 'java'
  };  

  language: string;
  loader = true;
  code: string= '';
  selectedProblem?: Problem;

  constructor() {
    this.language = 'JAVA';
   }

  ngOnInit(): void {
    this.updateSolution();  
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
    }
  }

}
