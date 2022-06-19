import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { AlertService } from 'src/app/common/component/common/alert/alert-service.service';
import { LoaderService } from 'src/app/common/component/common/loader/loader.service';
import { ChangePasswordModel } from 'src/app/user/model/change-password.model';
import { User } from 'src/app/user/model/user.model';
import { UserService } from 'src/app/user/service/user.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html'
})
export class ResetPasswordComponent implements OnInit {

  user? : User;
  newPassword? : string;

  constructor(private _bsModalRef: BsModalRef,
    private userService : UserService,
    private alertService : AlertService,
    private loaderService : LoaderService) { 
    
  }

  ngOnInit(): void {
  }

  resetPassword(){
    if(!this.newPassword || this.newPassword == ''){
      return this.alertService.warn("Please enter password");
    }
    if(this.newPassword.trim().length < 6){
      return this.alertService.warn("Password must be at least 6 characters");
    }    
    if(this.user){
      this.loaderService.show();    
      let resetPassword : ChangePasswordModel = {
          newPassword : this.newPassword.trim()
      }
      this.userService.resetPassword(this.user?.id,resetPassword).subscribe(response => {    
        this.loaderService.hide();       
        if(response){                   
            this.alertService.success("Password updated successfully");      
            this.onCancel();        
          }                
        }, error => {
          this.loaderService.hide();       
      });  
    }    
  }

  public onCancel(): void {      
    this._bsModalRef.hide();
  }

}
