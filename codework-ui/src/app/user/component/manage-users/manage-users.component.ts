import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from 'src/app/common/component/common/alert/alert-service.service';
import { LoaderService } from 'src/app/common/component/common/loader/loader.service';
import { User } from '../../model/user.model';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html'
})
export class ManageUsersComponent implements OnInit {

  users : Array<User> = [];

  constructor(private router : Router,
    private userService : UserService, 
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

}
