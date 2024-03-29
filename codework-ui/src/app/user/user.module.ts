import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserComponent } from './user.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppCommonModule } from '../common/common.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { ModalModule } from 'ngx-bootstrap/modal';
import { ManageUsersComponent } from './component/manage-users/manage-users.component';
import { UserRoutingModule } from './user-routing.module';
import { ChangePasswordComponent } from './component/change-password/change-password.component';
import { BulkUserUploadComponent } from './component/bulk-user-upload/bulk-user-upload.component';
import { CreateUserComponent } from './component/create-user/create-user.component';
import { ResetPasswordComponent } from './component/manage-users/reset-password/reset-password.component';
import { FilterPipe } from './pipe/filter-pipe';

@NgModule({
  declarations: [
    UserComponent,
    ManageUsersComponent,
    ChangePasswordComponent,
    BulkUserUploadComponent,
    CreateUserComponent,
    ResetPasswordComponent,
    FilterPipe
  ],
  imports: [
    UserRoutingModule,
    CommonModule,
    FormsModule,      
    AppCommonModule,                
    ReactiveFormsModule,        
    NgbModule,        
    FormsModule,    
    SweetAlert2Module.forRoot(),    
    ModalModule.forRoot()    
  ]
})
export class UserModule { }
