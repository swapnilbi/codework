import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { timer } from 'rxjs';
import { tap } from 'rxjs/operators';
import { UserProfile } from './authentication/model/user-profile.model';
import { AuthenticationService } from './authentication/service/authentication.service';
import { UserAuthService } from './authentication/service/user-auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

  userProfile: UserProfile | null = null;

  constructor(private userAuthService : UserAuthService, 
    private authenticationService : AuthenticationService,
    private router: Router) { }

  ngOnInit(): void {    
    this.userAuthService.getUser().subscribe(response =>{
      if(!response){
        this.router.navigate(['auth']);
      }else{        
        this.refreshTokenChecker();      
      }
    });
  }

  refreshTokenChecker(){
    const reloadInterval = 15 * 60 * 1000; // 15 mins
    timer(0, reloadInterval).pipe(
      tap(() => this.refreshToken())
    ).subscribe()
  }

  refreshToken(){
      if(this.userAuthService.getToken()){
        this.authenticationService.refreshToken().subscribe(response =>{
          if(response && response.token){
            this.userAuthService.setToken(response.token);
          }else{
            this.router.navigate(['auth']);
          }
      })
    }    
  }

}
