import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserAuthService } from './service/user-auth.service';
import { SidebarService } from './component/common/sidebar/sidebar.service';
import { UserProfile } from './model/user-profile.model';
import { AuthenticationService } from './service/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  
  isAuthenticated: boolean = false;
  userProfile: UserProfile | null = null;

  constructor(public sidebarService: SidebarService, private authenticationService : AuthenticationService, private userAuthService : UserAuthService, private router: Router) { }

  ngOnInit(): void {    
    this.userAuthService.getUser().subscribe(response =>{
      if(response){                
        this.userProfile =  response;
        this.isAuthenticated = true;
      }else{
        this.isAuthenticated = false;
        this.router.navigate(['login']);           
      }      
    });    
  }
  
  toggleSidebar() {
    this.sidebarService.setSidebarState(!this.sidebarService.getSidebarState());
  }
  
  getSideBarState() {
    return this.sidebarService.getSidebarState();
  }

  hideSidebar() {
    this.sidebarService.setSidebarState(true);
  }
}
