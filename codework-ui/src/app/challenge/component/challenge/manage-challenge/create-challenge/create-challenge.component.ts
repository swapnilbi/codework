import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from 'src/app/common/component/common/alert/alert-service.service';
import { LoaderService } from 'src/app/common/component/common/loader/loader.service';
import { ChallengeService } from 'src/app/challenge/service/challenge.service';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { HtmlPreviewComponent } from '../html-preview/html-preview.component';

@Component({
  selector: 'app-create-challenge',
  templateUrl: './create-challenge.component.html'
})
export class CreateChallengeComponent implements OnInit {

  challengeId : any;
  challengeForm: FormGroup;
  htmlPreviewModalRef!: BsModalRef;   

  constructor(private route: ActivatedRoute,
    private router : Router,
    private modalService: BsModalService,
    private challengeService : ChallengeService,
    private location : Location,
    private fb:FormBuilder,
    private alertService : AlertService, 
    private loaderService: LoaderService) {

      this.challengeForm = this.fb.group({
        "name": new FormControl("", Validators.required),   
        "bannerImage": new FormControl(""),               
        "shortDescription": new FormControl(null, Validators.required),
        "longDescription": new FormControl(null, Validators.required),        
        "startDate": new FormControl(null, Validators.required),                
        "endDate": new FormControl(null, Validators.required)                
      });   
      this.challengeId = this.route.snapshot.paramMap.get('challengeId');        
     }

  ngOnInit(): void {
    if(this.challengeId){
      this.challengeService.getChallengeDetails(this.challengeId).subscribe(response => {    
        if(response){         
            this.loaderService.hide();            
            let challengeForm = {      
              "name": response.name,
              "bannerImage": response.bannerImage,
              "shortDescription": response.shortDescription,
              "longDescription": response.longDescription,
              "startDate": new Date(response.startDate),
              "endDate": new Date(response.endDate)
            }  
            this.challengeForm.patchValue(challengeForm);
          }                
        }, error => {
          this.loaderService.hide();       
      }); 
    }else{
      this.setDefaultValues()
    }
  }

  back(){        
    this.location.back();
  }

  previewLongDescription(longDescription : string){
    const initialState = {
      'code' : longDescription
    };
    this.htmlPreviewModalRef = this.modalService.show(HtmlPreviewComponent, {initialState, class: 'modal-xl'});  
  }

  showChallenge(){    
    this.router.navigate(['challenge/manage']);  
  }

  updateChallenge(challengeForm : any){           
    challengeForm = this.getChallengeForm(challengeForm);
    challengeForm.id = this.challengeId;    
    this.loaderService.show();       
    this.challengeService.updateChallenge(challengeForm).subscribe(response => {    
      if(response){         
          this.loaderService.hide();       
          this.alertService.success("Challenge has been updated successfully");
          this.location.back();
        }                
      }, error => {
        this.loaderService.hide();       
    }); 
  }

  createChallenge(challengeForm : any){       
    challengeForm = this.getChallengeForm(challengeForm);    
    this.loaderService.show();          
    this.challengeService.createChallenge(challengeForm).subscribe(response => {    
      if(response){         
          this.loaderService.hide();       
          this.alertService.success("Challenge has been created successfully");
          this.showChallenge();
        }                
      }, error => {
        this.loaderService.hide();       
    });     
  }

  getChallengeForm(challengeForm : any){
    console.log(challengeForm);
    return challengeForm;
  }

  setDefaultValues(){
    let challengeForm = {      
      
    }
    this.challengeForm.patchValue(challengeForm);
  }

}
