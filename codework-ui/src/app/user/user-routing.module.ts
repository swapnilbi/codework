import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ManageUsersComponent } from './component/manage-users/manage-users.component';
import { UserComponent } from './user.component';

const routes: Routes = [  
  {
    path: '',
        component: UserComponent,
        children: [
          {
            path : "manage",
            component : ManageUsersComponent
          },          
          {
            path : "",
            component : ManageUsersComponent
          }
        ]  
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
