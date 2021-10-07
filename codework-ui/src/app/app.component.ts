import { Component } from '@angular/core';
import { SidebarService } from './component/common/sidebar/sidebar.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  
  constructor(public sidebarService: SidebarService) { }
  
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
