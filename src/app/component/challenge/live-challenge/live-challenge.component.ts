import { Component, OnInit } from '@angular/core';
import { DiffEditorModel } from 'ngx-monaco-editor';

@Component({
  selector: 'app-live-challenge',
  templateUrl: './live-challenge.component.html',
  styleUrls: ['./live-challenge.component.scss']
})
export class LiveChallengeComponent implements OnInit {

  selectedProblem: number;

  problemStatements = [
        { id: 1, description: 'Problem 1' },
        { id: 2, description: 'Problem 1' },        
    ];

  constructor() {
    this.selectedProblem = 1
   }

  ngOnInit(): void {
  }

}
