import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Remark } from 'src/app/challenge/model/response.model';
import { ChallengInstanceService } from 'src/app/challenge/service/challenge-instance.service';
import { AlertService } from 'src/app/common/component/common/alert/alert-service.service';
import { LoaderService } from 'src/app/common/component/common/loader/loader.service';
import { UserService } from 'src/app/user/service/user.service';

@Component({
  selector: 'app-bulk-upload-solutions',
  templateUrl: './bulk-upload-solutions.component.html',
  styleUrls: ['./bulk-upload-solutions.component.scss']
})
export class BulkUploadSolutionsComponent implements OnInit {

  file?: File | null;  
  instanceId : any;
  remarks?: Array<Remark>;  
  isSubmitted : boolean = false;

  constructor(private challengeInstanceService : ChallengInstanceService,
    private alertService : AlertService, 
    private location : Location,
    private route: ActivatedRoute,
    private loaderService: LoaderService) { }

  ngOnInit(): void {
    this.instanceId = this.route.snapshot.paramMap.get('instanceId');    
  }

  onFileChange(event : any) {  
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      if(this.validateFile(file)){
         this.file = file;
      }            
    }
  }

  validateFile(file : File){
    if(!(file.type == "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" || file.type == "application/vnd.ms-excel")){
      this.alertService.error("Please select xlsx file");
      return false;
    }            
    return true;
  }

  back(){        
    this.location.back();
  }

  uploadSolutions(){
    this.isSubmitted  = true;
    this.remarks = [];
    if(this.file && this.validateFile(this.file)){
      this.loaderService.show();    
      this.challengeInstanceService.bulkUploadSolutions(this.file, this.instanceId).subscribe(response => {    
        if(response){         
            this.loaderService.hide();                   
            if(response.remarks && response.remarks.length > 0){
              this.alertService.error("Bulk upload failed");            
              this.remarks = response.remarks;
            }else{
              this.alertService.success("Solutions uploaded successfully");                     
            }            
          }                
        }, error => {        
          this.loaderService.hide();       
      }); 
    }         
  }

}
