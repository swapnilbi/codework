import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppCommonModule } from '../common/common.module';
import { LocalStorageService } from '../common/utility/local-storage';
import { LoginComponent } from './component/authentication/login/login.component';
import { UserRoutingModule } from './authentication-routing.module';
import { AuthenticationComponent } from './authentication.component';


@NgModule({
  declarations: [
    LoginComponent, 
    AuthenticationComponent    
  ],
  imports: [
    FormsModule,    
    AppCommonModule,
    UserRoutingModule    
  ],
  providers: [        
    LocalStorageService    
  ]  
})
export class AuthenticationModule { }
