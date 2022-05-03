import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Utility } from 'src/app/common/utility/utility';
import { Challenge } from 'src/app/model/challenge.model';
import { LiveChallenge } from 'src/app/model/live-challenge.model';
import { ProblemSolution } from 'src/app/model/problem-solution.model';
import { Problem } from 'src/app/model/problem.model';
import Swal from 'sweetalert2';
import { LoaderService } from '../../common/loader/loader.service';
import { LiveChallengeService } from '../live-challenge/live-challenge.service';

@Component({
  selector: 'free-text-editor',
  templateUrl: './free-text-editor.component.html',
  styleUrls: ['./free-text-editor.component.scss']
})
export class FreeTextEditorComponent implements OnInit {

  constructor(private liveChallengeService: LiveChallengeService, private loaderService: LoaderService) { }

  selectedProblem?: Problem;
  answer: string = "";
  liveChallengeDetails?: LiveChallenge;
  lastSavedSolution? : ProblemSolution;
  @Output() finishChallengeEvent = new EventEmitter<ProblemSolution>();

  ngOnInit(): void {
  }

  @Input() set liveChallenge(liveChallenge: LiveChallenge) {   
    this.liveChallengeDetails = liveChallenge;    
  }

  @Input() set problem(problem: Problem) {      
    this.selectedProblem  = problem;    
  }

  finishChallenge() {
    if(this.liveChallengeDetails?.challengeDetails && this.selectedProblem){
      let challengeSolution: ProblemSolution = {
        challengeId : this.liveChallengeDetails?.challengeDetails?.id,
        challengeInstanceId : this.liveChallengeDetails.challengeInstance?.id,        
        problemId : this.selectedProblem.id,          
        solution : unescape(this.answer)        
      }    
      this.finishChallengeEvent.emit(challengeSolution);    
    }  
  }

  getProblemSolution(){
    if(this.liveChallengeDetails?.challengeDetails && this.selectedProblem){   
      let challengeSolution: ProblemSolution = {
          challengeId : this.liveChallengeDetails.challengeDetails.id,
          challengeInstanceId : this.liveChallengeDetails.challengeInstance?.id,          
          problemId : this.selectedProblem.id,          
          solution : unescape(this.answer)
      }
      return challengeSolution;
    }
    return null;
  }

  saveSolution(showMessage : boolean){    
    let challengeSolution = this.getProblemSolution();
    if(challengeSolution && ( Utility.getHashCode(JSON.stringify(challengeSolution)) != Utility.getHashCode(JSON.stringify(this.lastSavedSolution)))){                         
      this.lastSavedSolution = Object.assign({}, challengeSolution);
      this.liveChallengeService.saveProblemSolution(challengeSolution).subscribe(response => {    
        if(response){
          if(showMessage){
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Your solution has been saved',
              showConfirmButton: false,
              timer: 2000
            })
          }
        }                
      }, error => {
        this.loaderService.hide();       
      });      
    }    
  }


}
