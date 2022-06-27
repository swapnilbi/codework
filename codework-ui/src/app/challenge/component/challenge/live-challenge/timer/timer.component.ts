import { Component, Input, OnInit } from '@angular/core';
import { LiveChallenge } from 'src/app/challenge/model/live-challenge.model';

@Component({
  selector: 'app-timer',
  templateUrl: './timer.component.html',
  styleUrls: ['./timer.component.scss']
})
export class TimerComponent implements OnInit {

  constructor() { }

  startTimer : number = 0;

  @Input() set startTime(startTime: Date | undefined) {           
    if(startTime){
      var currentTime = new Date();                  
      var difference = (currentTime.getTime() -  new Date(startTime).getTime()) / 1000;            
      this.startTimer = difference;
    }
  }

  ngOnInit(): void {

  }

}
