import { ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { Challenge } from 'src/app/model/challenge.model';
import { LoaderService } from '../../common/loader/loader.service';
import { ChallengeService } from './challenge.service';

@Component({
  selector: 'app-view-challenge',
  templateUrl: './view-challenge.component.html',
  styleUrls: ['./view-challenge.component.scss']
})
export class ViewChallengeComponent implements OnInit {

  constructor(private challengeService : ChallengeService, private loaderService: LoaderService) { }

  public challengeList: Array<Challenge> = [];

  ngOnInit(): void {
    this.loaderService.show();
    this.challengeService.getActiveChallenges().subscribe(response => {
      this.loaderService.hide();
      this.challengeList = response;      
    }, error => {
       // hide loader
    }
    );

  }


}
