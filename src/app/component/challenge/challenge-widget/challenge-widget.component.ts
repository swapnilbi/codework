import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'challenge-widget',
  templateUrl: './challenge-widget.component.html',
  styleUrls: ['./challenge-widget.component.scss']
})
export class ChallengeWidgetComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  registerChallenge(){
    this.router.navigate(['/challenge/details']);
  }

}
