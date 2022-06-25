import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Challenge } from 'src/app/challenge/model/challenge.model';
import { ProblemType } from 'src/app/challenge/model/problem.model';

@Component({
  selector: 'app-challenge-instruction',
  templateUrl: './challenge-instruction.component.html',
  styleUrls: ['./challenge-instruction.component.scss']
})
export class ChallengeInstructionComponent implements OnInit {

  @Input() challenge?: Challenge;  
  @Output() challengeStartEvent = new EventEmitter<string>();
  instructionsList : Array<string> = [];

  @Input() set problemTypes(problemTypes: Array<ProblemType> | undefined) {       
    console.log(problemTypes);
    if(problemTypes){
      this.instructionsList = [];
      let _this = this;
      problemTypes.forEach(function (problemType: any) {       
        let instruction = _this.getQuestionSpecificInstructions(problemType);
        if(instruction){
          _this.instructionsList.push(instruction);
        }        
      });      
    }
  }

  startChallenge() {
    this.challengeStartEvent.emit("Start challenge");
  }

  getQuestionSpecificInstructions(problemType : ProblemType){
    let instruction = null;
    if(this.challenge){
      this.challenge.questionSpecificInstructions?.forEach(function (questionSpecificInstruction: any) {         
          if(questionSpecificInstruction.problemType == problemType){
            instruction = questionSpecificInstruction.instruction;
          }
      })
    }  
    return instruction;  
  }
  
  constructor() {    
  }

  ngOnInit(): void {
    
  }


}
