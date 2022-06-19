import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { AlertService } from 'src/app/common/component/common/alert/alert-service.service';
import { LoaderService } from 'src/app/common/component/common/loader/loader.service';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html'
})
export class CreateUserComponent implements OnInit {

  userId : any;
  rolesList : Array<string> = [
    'USER',
    'ADMIN'
  ];
  genderList : Array<string> = [
    'MALE',
    'FEMALE'
  ];
  userForm: FormGroup;

  constructor(private route: ActivatedRoute,    
    private router : Router,    
    private location : Location,
    private fb:FormBuilder,
    private userService : UserService,
    private alertService : AlertService, 
    private loaderService: LoaderService) {      
      this.userForm = this.fb.group({
        "fullName": new FormControl("", Validators.required),
        "email": new FormControl("", [Validators.required, Validators.email]),
        "gender": new FormControl(null, Validators.required),
        "username" : new FormControl(null, [Validators.required, Validators.minLength(5)]),
        "password" :  new FormControl(null, [Validators.required, Validators.minLength(6)]),                
        "roles" : new FormControl(['USER'], Validators.required)               
     });     
  }

  ngOnInit(): void {
    this.userId = this.route.snapshot.paramMap.get('userId');        
    if(this.userId){
      this.userService.getUser(this.userId).subscribe(user => {  
        this.userForm.patchValue(user);
      });
    }                                        
  }

  validateUserForm(formGroup: FormGroup) {
    for (const key in formGroup.controls) {
        if (formGroup.controls.hasOwnProperty(key)) {
            const control: FormControl = <FormControl>formGroup.controls[key];
            if (Object.keys(control).includes('controls')) {
                const formGroupChild: FormGroup = <FormGroup>formGroup.controls[key];
                this.validateUserForm(formGroupChild);
            }
            control.markAsTouched();
        }
    } 
  }

  rolesCompare(c1: any, c2: any): boolean {    
    return c1 && c2 ? c1 == c2 : false;
  }

  back(){        
    this.location.back();
  }

  createUser(userForm : any){     
    this.validateUserForm(this.userForm);
    if(this.userForm.valid){
      userForm = this.getUserForm(userForm);    
      this.loaderService.show();       
      this.userService.createUser(userForm).subscribe(response => {    
        if(response){         
            this.loaderService.hide();       
            this.alertService.success("User created successfully");
            this.showUsers();
          }                
        }, error => {        
          this.loaderService.hide();       
      }); 
    }    
  }

  getUserForm(userForm : any){    
    return userForm;
  }

  updateUser(userForm : any){           
    userForm = this.getUserForm(userForm);
    userForm.id = this.userId;    
    this.loaderService.show();       
    this.userService.updateUser(userForm).subscribe(response => {    
      if(response){         
          this.loaderService.hide();       
          this.alertService.success("User updated successfully");
          this.location.back();
        }                
      }, error => {
        this.loaderService.hide();       
    }); 
  }

  showUsers(){    
    var url = 'user/manage';
    this.router.navigateByUrl(url);    
  }

}
