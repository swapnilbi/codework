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

@NgModule({
  declarations: [
    UserComponent,
    ManageUsersComponent
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
