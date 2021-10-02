import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Challenge } from 'src/app/model/challenge.model';

@Component({
  selector: 'challenge-widget',
  templateUrl: './challenge-widget.component.html',
  styleUrls: ['./challenge-widget.component.scss']
})
export class ChallengeWidgetComponent implements OnInit {


  @Input() challenge!: Challenge;

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  viewChallenge(){
    var url = '/challenge/'+this.challenge.id+'/details';
    this.router.navigateByUrl(url);
  }

}
