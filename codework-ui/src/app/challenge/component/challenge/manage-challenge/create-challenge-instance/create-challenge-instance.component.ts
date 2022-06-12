import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from 'src/app/common/component/common/alert/alert-service.service';
import { LoaderService } from 'src/app/common/component/common/loader/loader.service';
import { ChallengInstanceService } from 'src/app/challenge/service/challenge-instance.service';

@Component({
  selector: 'app-create-challenge-instance',
  templateUrl: './create-challenge-instance.component.html'  
})
export class CreateChallengeInstanceComponent implements OnInit {

  instanceId : any;
  challengeId : any;
  challengeInstanceForm: FormGroup;

  constructor(private route: ActivatedRoute,
    private router : Router,
    private challengeInstanceService : ChallengInstanceService,
    private location : Location,
    private fb:FormBuilder,
    private alertService : AlertService, 
    private loaderService: LoaderService) {

      this.challengeInstanceForm = this.fb.group({
        "name": new FormControl("", Validators.required),       
        "type": new FormControl(null, Validators.required),
        "startDate": new FormControl(null, Validators.required),        
        "endDate": new FormControl(null, Validators.required)                
     });   

    this.instanceId = this.route.snapshot.paramMap.get('instanceId');        
    this.challengeId = this.route.snapshot.paramMap.get('challengeId');    
   }

  ngOnInit(): void {
    if(this.instanceId){
      this.loaderService.show();       
      this.challengeInstanceService.getChallengeInstance(this.instanceId).subscribe(response => {    
        if(response){         
            this.loaderService.hide();       
            let instanceForm = {
              "name": response.name,
              "type": response.type,
              "startDate": new Date(response.startDate),              
              "endDate": new Date(response.endDate)
            }
            this.challengeInstanceForm.patchValue(instanceForm);
          }                
        }, error => {
          this.loaderService.hide();       
      }); 
    }
  }

  back(){        
    this.location.back();
  }

  showChallengeInstances(){
    var url = 'challenge/'+this.challengeId+'/instance/manage';              
    this.router.navigateByUrl(url);  
  }

  updateChallengeInstance(challengeInstanceForm : any){           
    challengeInstanceForm = this.getChallengeInstanceForm(challengeInstanceForm);
    challengeInstanceForm.id = this.instanceId;    
    this.loaderService.show();       
    this.challengeInstanceService.updateChallengeInstance(challengeInstanceForm).subscribe(response => {    
      if(response){         
          this.loaderService.hide();       
          this.alertService.success("Challenge instance has been updated successfully");
          this.location.back();
        }                
      }, error => {
        this.loaderService.hide();       
    }); 
  }

  createChallengeInstance(challengeInstanceForm : any){       
    challengeInstanceForm = this.getChallengeInstanceForm(challengeInstanceForm);
    challengeInstanceForm.challengeId = this.challengeId;    
    this.loaderService.show();          
    this.challengeInstanceService.createChallengeInstance(challengeInstanceForm).subscribe(response => {    
      if(response){         
          this.loaderService.hide();       
          this.alertService.success("Challenge instance has been created successfully");
          this.showChallengeInstances();
        }                
      }, error => {
        this.loaderService.hide();       
    });     
  }

  getChallengeInstanceForm(challengeInstanceForm : any){    
    return challengeInstanceForm;
  }

  setDefaultValues(){
    let challengeInstance = {      
      
    }
    this.challengeInstanceForm.patchValue(challengeInstance);
  }

}
