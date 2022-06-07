import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [    
  {
    path: 'login', 
    loadChildren: () => import('./user/user.module').then(m => m.UserModule)  
  },
  {
    path: 'challenges', 
    loadChildren: () => import('./challenge/challenge.module').then(m => m.ChallengeModule)  
  },
  { 
    path: '', 
    redirectTo: 'login', 
    pathMatch: 'full' 
  }  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
