import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './component/authentication/login/login.component';
import { AuthenticationComponent } from './authentication.component';
import { SignupComponent } from './component/signup/signup.component';

const routes: Routes = [     
  {
    path: 'challenges', 
    loadChildren: () => import('../challenge/challenge.module').then(m => m.ChallengeModule)  
  },    
  {
    path: "",
    component: AuthenticationComponent,
    children: [
      {
        path : "",
        component : LoginComponent
      },
      {
        path : "login",
        component : LoginComponent
      },
      {
        path : "signup",
        component : SignupComponent
      },      
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
