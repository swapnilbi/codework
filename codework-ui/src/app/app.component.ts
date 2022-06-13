import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserProfile } from './authentication/model/user-profile.model';
import { UserAuthService } from './authentication/service/user-auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

  userProfile: UserProfile | null = null;

  constructor(private userAuthService : UserAuthService, private router: Router) { }

  ngOnInit(): void {
    this.userAuthService.getUser().subscribe(response =>{
      if(!response){
        this.router.navigate(['login']);
      }
    });
  }

}
