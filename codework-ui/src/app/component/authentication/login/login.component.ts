import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../../service/authentication.service';
import { AlertService } from '../../common/alert/alert-service.service';
import { LoaderService } from '../../common/loader/loader.service';
import { UserLogin } from '../../../model/user-login.model';
import { Router } from '@angular/router';
import { UserAuthService } from '../../../service/user-auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username : string = '';
  password : string = '';

  constructor(private authenticationService : AuthenticationService, 
    private userAuthService : UserAuthService,
    private loaderService : LoaderService, 
    private alertService : AlertService, 
    private router: Router ) {

  }

  ngOnInit(): void {        
    
  }

  login(){
    if(this.username!='' && this.password!=''){ 
      let userLogin : UserLogin = {
        username : this.username,
        password :  this.password
      }
      this.loaderService.show();
      this.authenticationService.authenticate(userLogin).subscribe(response => {    
        this.userAuthService.setToken(response.token);
        this.authenticationService.getUserProfile().subscribe(response => {    
          this.loaderService.hide();          
          this.userAuthService.setUser(response);                
          this.router.navigate(['challenges']);                      
        });        
      }, error => {        
        this.loaderService.hide();
      });   
    }
  }

}
