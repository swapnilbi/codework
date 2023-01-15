import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppCommonModule } from '../common/common.module';
import { LocalStorageService } from '../common/utility/local-storage';
import { LoginComponent } from './component/authentication/login/login.component';
import { UserRoutingModule } from './authentication-routing.module';
import { AuthenticationComponent } from './authentication.component';
import { SignupComponent } from './component/signup/signup.component';


@NgModule({
  declarations: [
    LoginComponent, 
    AuthenticationComponent, SignupComponent    
  ],
  imports: [
    FormsModule,    
    ReactiveFormsModule,
    AppCommonModule,
    UserRoutingModule    
  ],
  providers: [        
    LocalStorageService    
  ]  
})
export class AuthenticationModule { }
