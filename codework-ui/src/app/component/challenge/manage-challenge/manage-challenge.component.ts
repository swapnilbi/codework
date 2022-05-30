import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Challenge } from 'src/app/model/challenge.model';
import { ChallengeService } from '../../../service/challenge.service';
import { LoaderService } from '../../common/loader/loader.service';

@Component({
  selector: 'app-manage-challenge',
  templateUrl: './manage-challenge.component.html',
  styleUrls: ['./manage-challenge.component.scss']
})
export class ManageChallengeComponent implements OnInit {

  challenges : Array<Challenge>;

  constructor(private challengeService : ChallengeService,     
    private loaderService: LoaderService,
    private router : Router) {
    this.challenges = [];
  }

  ngOnInit(): void {
    this.challengeService.getChallengeList().subscribe(response => {
      this.challenges = response;
    })
  }

  viewChallengeInstance(challenge : Challenge){
    var url = '/challenge/'+challenge.id+'/instance/manage';
    this.router.navigateByUrl(url);
  }


}
