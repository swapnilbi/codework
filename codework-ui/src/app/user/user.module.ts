import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppCommonModule } from '../common/common.module';
import { LoaderService } from '../common/component/common/loader/loader.service';
import { LocalStorageService } from '../common/utility/local-storage';
import { LoginComponent } from './component/authentication/login/login.component';
import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';


@NgModule({
  declarations: [
    LoginComponent, 
    UserComponent    
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
export class UserModule { }
