import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AlertType } from './alert-model';
import { AlertService } from './alert-service.service';
import Swal from 'sweetalert2/dist/sweetalert2.js';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss']
})
export class AlertComponent implements OnInit {

  @Input() id = 'default-alert';
  @Input() fade = true;
  
  alertSubscription?: Subscription;
  routeSubscription?: Subscription;

  constructor(private router: Router, private alertService: AlertService) { }

  ngOnInit(): void {

    this.alertSubscription = this.alertService.onAlert(this.id)
            .subscribe(alert => {
              let title = alert.title;
              let type:any = alert.type ? alert.type.toString().toLowerCase() : 'success';
              if(!title){
                switch(alert.type){
                  case AlertType.Error:
                    title = 'Oops...';
                    break;
                  case AlertType.Warning:
                    title = 'Warning!';
                    break;                
                  case AlertType.Success:
                  case AlertType.Info:
                    title = 'Done!';
                    break;                
                }
              }               
              Swal.fire(
                title,
                alert.message,
                type
              )              
           });
        
  }

  ngOnDestroy() {
    // unsubscribe to avoid memory leaks
    if(this.alertSubscription){
      this.alertSubscription.unsubscribe();
    }    
    if(this.routeSubscription){
      this.routeSubscription.unsubscribe();
    }    
  }


}
