import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from 'src/app/common/component/common/alert/alert-service.service';
import { LoaderService } from 'src/app/common/component/common/loader/loader.service';
import { ChallengeInstance } from 'src/app/challenge/model/challenge-instance.model';
import Swal from 'sweetalert2';
import { ChallengInstanceService } from '../../../../service/challenge-instance.service';

@Component({
  selector: 'app-manage-challenge-instance',
  templateUrl: './manage-challenge-instance.component.html',
  styleUrls: ['./manage-challenge-instance.component.scss']
})
export class ManageChallengeInstanceComponent implements OnInit {

  challengInstanceList : Array<ChallengeInstance>;
  challengeId? : any;

  constructor(private challengeInstanceService : ChallengInstanceService, 
    private route: ActivatedRoute, 
    private location : Location,
    private router: Router, 
    private alertService : AlertService, 
    private loaderService: LoaderService) {
    this.challengInstanceList = [];
  }

  ngOnInit(): void {
    this.challengeId = this.route.snapshot.paramMap.get('challengeId');      
    this.getChallengeInstanceList();  
  }

  getChallengeInstanceList(){
    this.challengeInstanceService.getChallengeInstanceList(this.challengeId).subscribe(response => {
      this.challengInstanceList = response;
    })
  }

  editChallengeInstance(challengeInstance : ChallengeInstance){
    var url = 'challenge/instance/'+challengeInstance.id+'/edit';
    this.router.navigateByUrl(url);
  }

  stopChallengeInstance(instance : ChallengeInstance){
    this.loaderService.show();       
    this.challengeInstanceService.stopChallengeInstance(instance.id).subscribe(response => {    
      if(response){ 
        instance.instanceStatus = response.instanceStatus;               
        this.loaderService.hide();       
        this.alertService.success('Challenge instance stopped successfully');
        }                
      }, error => {
        this.loaderService.hide();       
      }); 
  }

  deleteChallengeInstance(challengeInstance : ChallengeInstance){
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
        this.challengeInstanceService.deleteChallengeInstance(challengeInstance.id).subscribe(response => {    
          if(response){         
            this.loaderService.hide();       
            this.alertService.success('Challenge instance deleted successfully');
            this.getChallengeInstanceList();
            }                
          }, error => {
            this.loaderService.hide();       
          });   
      }
    })    
  }

  startChallengeInstance(instance : ChallengeInstance){
    this.loaderService.show();       
    this.challengeInstanceService.startChallengeInstance(instance.id).subscribe(response => {    
      if(response){ 
        instance.instanceStatus = response.instanceStatus;               
        this.loaderService.hide();       
        this.alertService.success('Challenge instance started successfully');
        }                
      }, error => {
        this.loaderService.hide();       
      }); 
  }

  createChallengeInstance(){
    var url = 'challenge/'+this.challengeId+'/instance/create';
    this.router.navigateByUrl(url); 
  }

  manageProblems(instance : ChallengeInstance){    
    var url = 'challenge/instance/'+instance.id+'/problem/manage';
    this.router.navigateByUrl(url);    
  }

  back(){        
    this.location.back();
  }

}

