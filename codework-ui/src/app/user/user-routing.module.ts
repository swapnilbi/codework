import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BulkUserUploadComponent } from './component/bulk-user-upload/bulk-user-upload.component';
import { ChangePasswordComponent } from './component/change-password/change-password.component';
import { CreateUserComponent } from './component/create-user/create-user.component';
import { ManageUsersComponent } from './component/manage-users/manage-users.component';
import { UserComponent } from './user.component';

const routes: Routes = [  
  {
    path: 'user',
        component: UserComponent,
        children: [
          {
            path : "manage",
            component : ManageUsersComponent
          },
          {
            path : "create",
            component : CreateUserComponent
          },
          {
            path : ":userId/edit",
            component : CreateUserComponent
          },                    
          {
            path : "change-password",
            component : ChangePasswordComponent
          },
          {
            path : "bulk-upload",
            component : BulkUserUploadComponent
          },                    
        ]  
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
