import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SidebarService } from '../common/component/common/sidebar/sidebar.service';

@Component({
  selector: 'app-root',
  templateUrl: './challenge.component.html'  
})
export class ChallengeComponent implements OnInit {
  
  constructor(private router: Router,     
    private sidebarService : SidebarService) { }

  ngOnInit(): void {    

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
