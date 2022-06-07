import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserProfile } from './user/model/user-profile.model';
import { AuthenticationService } from './user/service/authentication.service';
import { UserAuthService } from './user/service/user-auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  
  userProfile: UserProfile | null = null;

  constructor(private authenticationService : AuthenticationService, private userAuthService : UserAuthService, private router: Router) { }

  ngOnInit(): void {    
    this.userAuthService.getUser().subscribe(response =>{
      if(!response){                
        this.router.navigate(['login']);           
      }      
    });
  }

}
