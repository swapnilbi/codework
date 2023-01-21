import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbNavChangeEvent, NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { AgGridEvent, ColDef, FirstDataRenderedEvent, GridApi, GridReadyEvent, PostSortRowsParams } from 'ag-grid-community';
import { BsModalService } from 'ngx-bootstrap/modal';
import { UserProfile } from 'src/app/authentication/model/user-profile.model';
import { UserAuthService } from 'src/app/authentication/service/user-auth.service';
import { ChallengeInstance } from 'src/app/challenge/model/challenge-instance.model';
import { ChallengeLeaderboard } from 'src/app/challenge/model/challenge-leaderboard';
import { ChallengeSubscriptionStatus } from 'src/app/challenge/model/challenge-subscription.modal';
import { Challenge, ChallengeStatus } from 'src/app/challenge/model/challenge.model';
import { UserSubmission } from 'src/app/challenge/model/user-submission.model';
import { TimeTakenPipe } from 'src/app/challenge/pipe/time-taken-pipe';
import { ChallengInstanceService } from 'src/app/challenge/service/challenge-instance.service';
import { AlertService } from '../../../../common/component/common/alert/alert-service.service';
import { LoaderService } from '../../../../common/component/common/loader/loader.service';
import { ChallengeService } from '../../../service/challenge.service';
import { ViewSubmissionComponent } from '../manage-challenge/evaluate-challenge-instance/view-submission/view-submission.component';
import { AgGridRowNumberComponent } from './ag-grid-row-number/ag-grid-row-number.component';

@Component({
  selector: 'app-challenge-details',
  templateUrl: './challenge-details.component.html',  
  styleUrls: ['./challenge-details.component.scss']  
})
export class ChallengeDetailsComponent implements OnInit {

  private gridApi!: GridApi;
  public challenge?: Challenge;
  private userProfile?: UserProfile;
  public challengeLeaderboard?: ChallengeLeaderboard;
  public defaultColDef: ColDef = {
   
  };

  onGridReady(params: GridReadyEvent) {
     this.gridApi = params.api;         
     if(this.defaultColDef){
      this.gridApi.setColumnDefs(this.columnDefs);          
    }
  }

  onFirstDataRendered(params: FirstDataRenderedEvent) {
    params.api.sizeColumnsToFit();
  }

  public columnDefs: ColDef[] = [
    { headerName: 'Rank', cellRendererFramework: AgGridRowNumberComponent, width: 100 },            
    { field: 'userDetails.fullName', headerName: 'User', width: 300,
    cellRenderer: (params: any) => {      
      if(this.userProfile && this.userProfile.id == params.data.userDetails.id){
        return '<span class="badge badge-primary">'+params.data.userDetails.fullName+'</span>';
      }      
      return params.data.userDetails.fullName;
     }     
    }      
  ];

  public domLayout: 'normal' | 'autoHeight' | 'print' = 'autoHeight';

  public rowData!: any[];      

  constructor(private router: Router, 
    private route: ActivatedRoute, 
    private challengeInstanceService : ChallengInstanceService,
    private timeTakenPipe : TimeTakenPipe,
    private challengeService : ChallengeService, 
    protected alertService: AlertService, 
    private modalService: BsModalService,
    private userAuthService : UserAuthService, 
    private loaderService: LoaderService) {   
      this.userAuthService.getUser().subscribe(response =>{
        if(response){
          this.userProfile =  response;           
        }     
      });  
  }

  ngOnInit(): void {        
    let challengeId : any = this.route.snapshot.paramMap.get('id');    
    this.loaderService.show();
    this.challengeService.getChallengeDetails(challengeId).subscribe(response => {
      this.loaderService.hide();
      this.challenge = response;
    }, error => {
      this.loaderService.hide();       
    });    
  }

  exportAsExcel(){
    this.gridApi.exportDataAsExcel();
  }

  registerChallenge(){    
    if(this.challenge){
      this.loaderService.show();
      this.challengeService.registerChallenge(this.challenge?.id).subscribe(response => {
        this.challenge = response;
        if(response.challengeSubscription?.status == ChallengeSubscriptionStatus.REGISTERED){
          this.alertService.success("You have been successfully registered");    
        }              
        this.loaderService.hide();
      }, error => {
        this.loaderService.hide();         
      });
    }    
  }

  onNavChange(changeEvent: NgbNavChangeEvent) {
    if (changeEvent.nextId === 2) {
      this.getLeaderboard();
    }
  }

  getLeaderboard(){          
    if(!this.challengeLeaderboard && this.challenge?.id) {
      this.loaderService.show();
        this.challengeService.getChallengeLeaderboard(this.challenge?.id).subscribe(response => {
          this.challengeLeaderboard = response;    
          this.rowData = this.challengeLeaderboard.userSubmissionsList;
          if(this.challengeLeaderboard.challengeInstanceList){
            let _this = this;
            this.challengeLeaderboard.challengeInstanceList.forEach(function (challengeInstance: ChallengeInstance) {
              _this.columnDefs.push({
                colId : challengeInstance.id.toString(),
                headerName : challengeInstance.name,      
                sortable: true,          
                valueGetter: params => {         
                  const challengeInstancePoint = params.data.challengeInstancePoints.find((c : any) => {
                     return c.challengeInstanceId.toString() == params.colDef.colId;
                  });        
                  if(challengeInstancePoint) {
                      return challengeInstancePoint;
                  }
                  return null;
                },
                cellRenderer: (params: any) => {   
                  const challengeInstancePoint = params.data.challengeInstancePoints.find((c : any) => {
                      return c.challengeInstanceId.toString() == params.colDef.colId;
                  });        
                  if(challengeInstancePoint) {
                      let timeTaken = _this.timeTakenPipe.transform(challengeInstancePoint.timeTaken);
                      return '<b title="Time Taken : '+timeTaken+'">'+challengeInstancePoint.points+'</b>';
                  }                    
                  return '<b title="Not Evaluated">-</b>';
                },
                comparator: (valueA, valueB, nodeA, nodeB, isInverted) => {
                  if(valueA && valueB){                    
                      if (valueA.points == valueB.points) {
                        if (valueA.timeTaken == valueB.timeTaken) {
                            return 0;
                        }
                        return (valueA.timeTaken < valueB.timeTaken) ? 1 : -1;   
                      }
                      return (valueA.points > valueA.points) ? 1 : -1;                    
                  }
                  if(valueA && !valueB){
                    return 1;
                  }
                  if(!valueA && valueB){
                    return -1;
                  }
                  return 0;                  
                }
              })
            })  

            _this.columnDefs.push({
              colId : 'total',
              headerName : 'Total',      
              sortable: true,          
              valueGetter: params => {         
                let total  = 0;
                params.data.challengeInstancePoints.forEach((c : any) => {
                    if(c.points){
                      total += c.points;
                    }
                });                        
                return total;
              },
              cellRenderer: (params: any) => {                   
                let total  = 0;
                params.data.challengeInstancePoints.forEach((c : any) => {
                   if(c.points){
                     total += c.points;
                   }
                });                        
                return '<b>'+total+'</b>';
              }              
            })

            if(this.gridApi){
              this.gridApi.setColumnDefs(this.columnDefs);          
            }            
          }
          this.loaderService.hide();
        }, error => {
          this.loaderService.hide();         
        });
      }      
  }

  isRegistered(challenge : Challenge){
    return this.challenge && this.challenge.challengeSubscription;
  }

  isLiveChallenge(){
    return this.challenge && this.challenge.status == ChallengeStatus.LIVE && this.challenge.challengeSubscription && this.challenge.challengeSubscription.status ==  ChallengeSubscriptionStatus.REGISTERED;
  }

  startChallenge(userSubmission:  UserSubmission){    
    if(userSubmission){
      var url = '/challenge/'+userSubmission.challengeInstanceId+'/live';
      this.router.navigateByUrl(url);    
    }    
  }

  viewSubmission(userSubmission:  UserSubmission){    
    if(userSubmission){
      this.loaderService.show();
      this.challengeInstanceService.getUserSubmittedProblems(userSubmission.id).subscribe(response => {      
        this.loaderService.hide();
        const initialState = {
          viewMode : true,
          submittedProblems : response 
        };
        this.modalService.show(ViewSubmissionComponent, {initialState, class: 'modal-xl modal-dialog-scrollable'});              
        }, error => {
          this.loaderService.hide();
        }
      );    
    }    
  }

}
