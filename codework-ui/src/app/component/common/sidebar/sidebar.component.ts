import { Component, OnInit } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { SidebarService } from './sidebar.service';
import { Observable, of } from 'rxjs';
import { Router } from '@angular/router';
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

  selectedMenu: any;
  
  constructor(public sidebarservice: SidebarService, private router: Router) {
    this.menus = of([
      {
        "id" : 1,
        "title" : "Challenges",
        "index" : 1,
        "url" : "/challenges",
        "icon" : "fas fa-code",
        "active" : true,
        "showDropdown" : false,          
      }
      /*
      ,
      {
        "id" : 2,
        "title" : "Test",
        "index" : 2,
        "url" : "/challenges",
        "icon" : "fas fa-code",
        "subMenus" : [{
          "id" : 3,
          "title" : "Manage",
          "index" : 1,
          "icon" : "fas fa-code",
          "url" : "/challenge/manage"          
        }]         
      } 
      */
    ]);  
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

}
