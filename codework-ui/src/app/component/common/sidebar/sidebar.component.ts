import { Component, OnInit } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { SidebarService } from './sidebar.service';
import { Observable, of } from 'rxjs';
import { Router } from '@angular/router';
import { UserAuthService } from '../../../service/user-auth.service';
import { AuthenticationService } from '../../../service/authentication.service';
import { LoaderService } from '../loader/loader.service';
import { UserProfile } from 'src/app/model/user-profile.model';
import { SelectMultipleControlValueAccessor } from '@angular/forms';
// import { MenusService } from './menus.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
  animations: [
    trigger('slide', [
      state('up', style({ height: 0 })),
      state('down', style({ height: '*' })),
      transition('up <=> down', animate(200))
    ])
  ]
})
export class SidebarComponent implements OnInit {

  menus: Observable<any[]>;
  userProfile?: UserProfile;
  selectedMenu: any;
  
  constructor(public sidebarservice: SidebarService, 
    private router: Router,     
    private loaderService : LoaderService, 
    private userAuthService : UserAuthService, 
    private authenticationService : AuthenticationService) {  
    this.menus = of([]);  
    this.userAuthService.getUser().subscribe(response =>{
      if(response){
        this.userProfile =  response;  
        this.setMenus(this.userProfile.roles);
      }     
    });
  }

  setMenus(roles : Array<string>){    
      let menuConfig : any = [{
        "id" : 1,
        "title" : "Challenges",
        "index" : 1,
        "url" : "/challenges",
        "icon" : "fas fa-code",        
        "roles" : ["USER","ADMIN"],
        "subMenus" : [{
          "id" : 11,
          "title" : "Manage",
          "index" : 1,
          "icon" : "fas fa-code",
          "url" : "/challenge/manage",          
          "roles" : ["ADMIN"]
        }]
      }];
      let filteredMenuConfig = [];
      menuConfig.forEach(function (menu: any) {
        let hasRole : boolean = false;
        roles.forEach(function (role: string) {
            if(menu.roles.indexOf(role)!=-1){
               hasRole = true;
            }
        });
        if(hasRole){
          filteredMenuConfig.push(menu);
        }
        if(menu.subMenus){          
          menu.subMenus.forEach(function (subMenu: any) {
            let hasRole : boolean = false;
            roles.forEach(function (role: string) {
              if(subMenu.roles.indexOf(role)!=-1){
                 hasRole = true;
              }
            });
            if(!hasRole){
              const index = menu.subMenus.indexOf(subMenu, 0);
              if (index > -1) {
                menu.subMenus.splice(index, 1);
              }              
            }
          });          
        }        
      });
      this.menus = of(menuConfig); 
  }

  isSelected(menu : any){
    if(this.selectedMenu){
      return this.selectedMenu.id == menu.id;
    }
    return false;
  }

  toggleDropdown(menu : any){
      menu.showDropdown = !menu.showDropdown;
  }

  nevigate(menu : any){    
    this.selectedMenu = menu;
    this.router.navigateByUrl(menu.url);
  }
   
  ngOnInit() {
  }

  getSideBarState() {
    return this.sidebarservice.getSidebarState();
  }

  logout(){    
    this.loaderService.show();
    this.authenticationService.logout().subscribe(response => {    
      this.userAuthService.logout();            
    }, error => {        
      this.userAuthService.logout();      
      this.loaderService.hide();
    });
  }

}
