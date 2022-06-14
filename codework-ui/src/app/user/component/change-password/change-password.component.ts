import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from 'src/app/common/component/common/alert/alert-service.service';
import { LoaderService } from 'src/app/common/component/common/loader/loader.service';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html'  
})
export class ChangePasswordComponent implements OnInit {

  changePasswordForm: FormGroup;
  
  constructor(private route: ActivatedRoute,    
    private router : Router,    
    private location : Location,
    private fb:FormBuilder,
    private userService : UserService, 
    private alertService : AlertService, 
    private loaderService: LoaderService) {  

      this.changePasswordForm = this.fb.group({
        "oldPassword": new FormControl("", Validators.required),
        "newPassword": new FormControl("", Validators.required),
        "confirmPassword": new FormControl(null, Validators.required),        
     });     
  }

  changePassword(changePasswordInput : any){
    if(changePasswordInput.newPassword != changePasswordInput.confirmPassword){
      this.alertService.error("Password and confirm password does not match");
      return;
    }
    let changeaPasswordForm = {
      oldPassword : changePasswordInput.oldPassword,
      newPassword : changePasswordInput.newPassword
    }
    //this.loaderService.show();       
    this.userService.changePassword(changeaPasswordForm).subscribe(response => {    
      //this.loaderService.hide();       
      if(response){                   
          this.alertService.success("Password has been changed successfully");  
          this.changePasswordForm.reset();                  
        }                
      }, error => {
        this.loaderService.hide();       
    }); 
  }

  ngOnInit(): void {

  }

  back(){        
    this.location.back();
  }

}
