import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Challenge } from 'src/app/challenge/model/challenge.model';
import Swal from 'sweetalert2';
import { ChallengeService } from '../../../service/challenge.service';
import { AlertService } from '../../../../common/component/common/alert/alert-service.service';
import { LoaderService } from '../../../../common/component/common/loader/loader.service';

@Component({
  selector: 'app-manage-challenge',
  templateUrl: './manage-challenge.component.html'  
})
export class ManageChallengeComponent implements OnInit {
  
  challenges : Array<Challenge>;

  constructor(private challengeService : ChallengeService,     
    private loaderService: LoaderService,  
    private alertService : AlertService, 
    private location : Location,
    private router : Router) {
    this.challenges = [];
  }

  ngOnInit(): void {
    this.getChallengeList();
  }

  back(){        
    this.location.back();
  }

  getChallengeList(){
    this.loaderService.show();
    this.challengeService.getChallengeList().subscribe(response => {
      this.challenges = response;
      this.loaderService.hide();
    })
  }

  publishChallenge(challenge : Challenge){
    this.loaderService.show();       
    this.challengeService.publishChallenge(challenge.id).subscribe(response => {    
      if(response){ 
        challenge.status = response.status;               
        this.loaderService.hide();       
        this.alertService.success('Challenge published successfully');
        }                
      }, error => {
        this.loaderService.hide();       
      }); 
  }

 stopChallenge(challenge : Challenge){
    this.loaderService.show();       
    this.challengeService.stopChallenge(challenge.id).subscribe(response => {    
      if(response){ 
        challenge.status = response.status;               
        this.loaderService.hide();       
        this.alertService.success('Challenge stopped successfully');
        }                
      }, error => {
        this.loaderService.hide();       
      }); 
  }

  deleteChallenge(challenge : Challenge){
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes'
    }).then((result) => {
      if (result.isConfirmed) {        
        this.loaderService.show();       
        this.challengeService.deleteChallenge(challenge.id).subscribe(response => {    
          if(response){         
            this.loaderService.hide();       
            this.alertService.success('Challenge deleted successfully');
            this.getChallengeList();
            }                
          }, error => {
            this.loaderService.hide();       
          });   
      }
    })    
  }

  createChallenge(){    
    this.router.navigate(['challenge/create']);                      
  }

  editChallenge(challenge : Challenge){    
    var url = '/challenge/'+challenge.id+'/edit';
    this.router.navigateByUrl(url);
  }

  viewChallengeInstance(challenge : Challenge){
    var url = '/challenge/'+challenge.id+'/instance/manage';
    this.router.navigateByUrl(url);
  }


}
