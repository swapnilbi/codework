import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChallengeDetailsComponent } from './component/challenge/challenge-details/challenge-details.component';
import { LiveChallengeComponent } from './component/challenge/live-challenge/live-challenge.component';
import { ViewChallengeComponent } from './component/challenge/view-challenge/view-challenge.component';
import { ManageChallengeComponent } from './component/challenge/manage-challenge/manage-challenge.component';
import { ManageChallengeInstanceComponent } from './component/challenge/manage-challenge/manage-challenge-instance/manage-challenge-instance.component';
import { ManageProblemComponent } from './component/challenge/manage-challenge/manage-problem/manage-problem.component';
import { CreateProblemComponent } from './component/challenge/manage-challenge/create-problem/create-problem.component';
import { CreateChallengeInstanceComponent } from './component/challenge/manage-challenge/create-challenge-instance/create-challenge-instance.component';
import { CreateChallengeComponent } from './component/challenge/manage-challenge/create-challenge/create-challenge.component';
import { ChallengeComponent } from './challenge.component';

const routes: Routes = [  
  {
    path: '',
        component: ChallengeComponent,
        children: [
          {
            path : "challenges",
            component : ViewChallengeComponent
          },
          {
            path : "challenge/manage",
            component : ManageChallengeComponent
          },
          {
            path : "challenge/create",
            component : CreateChallengeComponent
          },
          {
            path : "challenge/:challengeId/edit",
            component : CreateChallengeComponent
          },  
          {
            path : "challenge/:challengeId/instance/manage",
            component : ManageChallengeInstanceComponent
          },
          {
            path : "challenge/:challengeId/instance/create",
            component : CreateChallengeInstanceComponent
          },
          {
            path : "challenge/instance/:instanceId/edit",
            component : CreateChallengeInstanceComponent
          },  
          {
            path : "challenge/instance/:id/problem/manage",
            component : ManageProblemComponent
          },
          {
            path : "challenge/instance/:instanceId/problem/create",
            component : CreateProblemComponent
          },
          {
            path : "challenge/instance/problem/:problemId/edit",
            component : CreateProblemComponent
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
            component : ViewChallengeComponent
          }
        ]  
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ChallengeRoutingModule { }
