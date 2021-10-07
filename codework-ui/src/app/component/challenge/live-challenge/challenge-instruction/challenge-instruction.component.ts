import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-challenge-instruction',
  templateUrl: './challenge-instruction.component.html',
  styleUrls: ['./challenge-instruction.component.scss']
})
export class ChallengeInstructionComponent implements OnInit {

  @Output() challengeStartEvent = new EventEmitter<string>();

  startChallenge() {
    this.challengeStartEvent.emit("Start challenge");
  }
  
  constructor() {    
  }

  ngOnInit(): void {
    
  }


}
