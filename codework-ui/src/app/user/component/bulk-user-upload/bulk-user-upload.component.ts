import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Remark } from 'src/app/challenge/model/response.model';
import { AlertService } from 'src/app/common/component/common/alert/alert-service.service';
import { LoaderService } from 'src/app/common/component/common/loader/loader.service';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-bulk-user-upload',
  templateUrl: './bulk-user-upload.component.html'  
})
export class BulkUserUploadComponent implements OnInit {

  file?: File | null;  
  remarks?: Array<Remark>;  
  isSubmitted : boolean = false;

  constructor(private userService : UserService,
    private alertService : AlertService, 
    private location : Location,
    private loaderService: LoaderService) { }

  ngOnInit(): void {

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

  uploadUsers(){
    this.isSubmitted  = true;
    this.remarks = [];
    if(this.file && this.validateFile(this.file)){
      this.loaderService.show();    
      this.userService.bulkUserUpload(this.file).subscribe(response => {    
        if(response){         
            this.loaderService.hide();                   
            if(response.remarks && response.remarks.length > 0){
              this.alertService.error("Bulk user upload failed");            
              this.remarks = response.remarks;
            }else{
              this.alertService.success("Users created successfully");                     
            }            
          }                
        }, error => {        
          this.loaderService.hide();       
      }); 
    }         
  }

}
