import { NgModule } from '@angular/core';
import { LoaderComponent } from './component/common/loader/loader.component';
import { CommonModule } from '@angular/common';
import { LoaderService } from './component/common/loader/loader.service';
import { SidebarComponent } from './component/common/sidebar/sidebar.component';
import { AlertComponent } from './component/common/alert/alert.component';
import { LocalStorageService } from './utility/local-storage';
import { FormsModule } from '@angular/forms';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@NgModule({
  declarations: [
    AlertComponent,
    LoaderComponent,
    SidebarComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MatProgressSpinnerModule,    
    CommonModule
  ],
  exports:[
    CommonModule,
    AlertComponent,
    LoaderComponent,
    SidebarComponent       
  ],  
  providers: [    
    LoaderService,
    LocalStorageService       
  ]   
})
export class AppCommonModule { }
