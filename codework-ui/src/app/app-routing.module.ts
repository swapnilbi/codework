import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChallengeDetailsComponent } from './component/challenge/challenge-details/challenge-details.component';
import { ChallengeInstructionComponent } from './component/challenge/live-challenge/challenge-instruction/challenge-instruction.component';
import { LiveChallengeComponent } from './component/challenge/live-challenge/live-challenge.component';
import { ViewChallengeComponent } from './component/challenge/view-challenge/view-challenge.component';

const routes: Routes = [
  {
    path : "challenges",
    component : ViewChallengeComponent
  },
  {
    path : "challenge/:id/details",
    component : ChallengeDetailsComponent
  },
  {
    path : "challenge/:id/live",
    component : LiveChallengeComponent
  },  
  {
    path : "",
    redirectTo : "challenges",
    pathMatch : "full"
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
