import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BsModalService } from 'ngx-bootstrap/modal';
import { AlertService } from 'src/app/common/component/common/alert/alert-service.service';
import { LoaderService } from 'src/app/common/component/common/loader/loader.service';
import { User } from '../../model/user.model';
import { UserService } from '../../service/user.service';
import { ResetPasswordComponent } from './reset-password/reset-password.component';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html'
})
export class ManageUsersComponent implements OnInit {

  searchKey: string = "";
  users : Array<User> = [];

  constructor(private router : Router,
    private userService : UserService, 
    private modalService: BsModalService,
    private location: Location,
    private alertService : AlertService, 
    private loaderService: LoaderService,
    private route: ActivatedRoute) {
    
  }



  back(){        
    this.location.back();
  }

  ngOnInit(): void {
    this.getUsers()
  }

  bulkUpload(): void {
    this.router.navigate(['user/bulk-upload']);                      
  }

  createUser(): void {
    this.router.navigate(['user/create']);                      
  }

  editUser(user : User): void {
    var url = '/user/'+user.id+'/edit';    
    this.router.navigateByUrl(url);                      
  }

  getUsers(){
    this.loaderService.show();
    this.userService.getUsers().subscribe(response => {
      this.users = response;
      this.loaderService.hide();
    })
  }

  enableUser(user : User){
    this.loaderService.show();       
    this.userService.enableUser(user.id).subscribe(response => {    
      if(response){ 
        user.active = response.active;               
        this.loaderService.hide();       
        this.alertService.success('User has been enabled');
        }                
      }, error => {
        this.loaderService.hide();       
      }); 
  }

  deleteUser(user : User){
    this.loaderService.show();       
    this.userService.deleteUser(user.id).subscribe(response => {                  
        this.loaderService.hide();       
        this.alertService.success('User has been deleted');      
        this.getUsers();
      }, error => {
        this.loaderService.hide();       
      }); 
  }

  disableUser(user : User){
    this.loaderService.show();       
    this.userService.disableUser(user.id).subscribe(response => {    
      if(response){ 
        user.active = response.active;               
        this.loaderService.hide();       
        this.alertService.success('User has been disabled');
        }                
      }, error => {
        this.loaderService.hide();       
      }); 
  }

  changePassword(user : User){
    const initialState = {
      user : user
    };
    this.modalService.show(ResetPasswordComponent, {initialState});  
  }

}
