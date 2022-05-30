import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChallengeDetailsComponent } from './component/challenge/challenge-details/challenge-details.component';
import { LoginComponent } from './component/authentication/login/login.component';
import { LiveChallengeComponent } from './component/challenge/live-challenge/live-challenge.component';
import { ViewChallengeComponent } from './component/challenge/view-challenge/view-challenge.component';
import { ManageChallengeComponent } from './component/challenge/manage-challenge/manage-challenge.component';
import { ManageChallengeInstanceComponent } from './component/challenge/manage-challenge/manage-challenge-instance/manage-challenge-instance.component';
import { ManageProblemComponent } from './component/challenge/manage-challenge/manage-problem/manage-problem.component';
import { CreateProblemComponent } from './component/challenge/manage-challenge/create-problem/create-problem.component';

const routes: Routes = [
  {
    path : "login",
    component : LoginComponent
  },
  {
    path : "challenges",
    component : ViewChallengeComponent
  },
  {
    path : "challenge/manage",
    component : ManageChallengeComponent
  },  
  {
    path : "challenge/:id/instance/manage",
    component : ManageChallengeInstanceComponent
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
    redirectTo : "login",
    pathMatch : "full"
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
