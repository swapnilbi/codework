import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../service/authentication.service';
import { AlertService } from '../../../common/component/common/alert/alert-service.service';
import { LoaderService } from '../../../common/component/common/loader/loader.service';
import { UserLogin } from '../../model/user-login.model';
import { Router } from '@angular/router';
import { UserAuthService } from '../../service/user-auth.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/user/service/user.service';
import { UserRegistration } from '../../model/user-register.model';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  signupForm: FormGroup;

  constructor(private authenticationService : AuthenticationService,     
    private fb:FormBuilder,
    private loaderService : LoaderService, 
    private alertService : AlertService, 
    private router: Router ) {

      this.signupForm = this.fb.group({
        "firstName": new FormControl("", [Validators.required, Validators.pattern('[a-zA-Z ]*')]),   
        "lastName": new FormControl("", [Validators.required, Validators.pattern('[a-zA-Z ]*')]),               
        "email" : new FormControl("", [Validators.required, Validators.email]),               		    
        "password" : new FormControl("", [Validators.required, Validators.minLength(6)]),               		    
        "confirmPassword": new FormControl(null, Validators.required)        
      },{
      validator: ConfirmPasswordValidator('password', 'confirmPassword')}); 

  }

  ngOnInit(): void {        
    
  }

  showLogin(){    
    this.router.navigate(['login']);  
  }

  signup(signupForm : any){
    let createUserForm : UserRegistration = {
        firstName : signupForm.firstName,
        lastName : signupForm.lastName,
        email : signupForm.email,
        password : signupForm.password
    }
    this.loaderService.show();              
    this.authenticationService.register(createUserForm).subscribe(response => {          
          this.loaderService.hide();                 
          this.alertService.success("Registration completed successfully");          
          this.showLogin();      
      }, error => {
        this.loaderService.hide();       
    });     
  }

}

export function ConfirmPasswordValidator(controlName: string, matchingControlName: string){
  return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];
      if (matchingControl.errors && !matchingControl.errors.confirmedValidator) {
          return;
      }
      if (control.value !== matchingControl.value) {
          matchingControl.setErrors({ confirmedValidator: true });
      } else {
          matchingControl.setErrors(null);
      }
  }
}