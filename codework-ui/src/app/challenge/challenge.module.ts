import { NgModule } from '@angular/core';
import { ManageChallengeInstanceComponent } from './component/challenge/manage-challenge/manage-challenge-instance/manage-challenge-instance.component';
import { ManageProblemComponent } from './component/challenge/manage-challenge/manage-problem/manage-problem.component';
import { CreateProblemComponent } from './component/challenge/manage-challenge/create-problem/create-problem.component';
import { CreateChallengeInstanceComponent } from './component/challenge/manage-challenge/create-challenge-instance/create-challenge-instance.component';
import { CreateChallengeComponent } from './component/challenge/manage-challenge/create-challenge/create-challenge.component';
import { ManageChallengeComponent } from './component/challenge/manage-challenge/manage-challenge.component';
import { FreeTextEditorComponent } from './component/challenge/free-text-editor/free-text-editor.component';
import { CustomInputComponent } from './component/challenge/live-challenge/custom-input/custom-input.component';
import { TestResultComponent } from './component/challenge/live-challenge/test-result/test-result.component';
import { ChallengeInstructionComponent } from './component/challenge/live-challenge/challenge-instruction/challenge-instruction.component';
import { CompileResultComponent } from './component/challenge/live-challenge/compile-result/compile-result.component';
import { CodeEditorComponent } from './component/challenge/code-editor/code-editor.component';
import { CodeEditorConfig } from './component/challenge/code-editor/code-editor.config';
import { ViewChallengeComponent } from './component/challenge/view-challenge/view-challenge.component';
import { ChallengeWidgetComponent } from './component/challenge/challenge-widget/challenge-widget.component';
import { ChallengeDetailsComponent } from './component/challenge/challenge-details/challenge-details.component';
import { LiveChallengeComponent } from './component/challenge/live-challenge/live-challenge.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { AgGridModule } from 'ag-grid-angular';
import { PerfectScrollbarConfigInterface, PerfectScrollbarModule, PERFECT_SCROLLBAR_CONFIG } from 'ngx-perfect-scrollbar';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AngularSplitModule } from 'angular-split';
import { MonacoEditorModule } from 'ngx-monaco-editor';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { ModalModule } from 'ngx-bootstrap/modal';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { ChallengeRoutingModule } from './challenge-routing.module';
import { AppCommonModule } from '../common/common.module';
import { ChallengeComponent } from './challenge.component';
import { CommonModule } from '@angular/common';
import { HtmlPreviewComponent } from './component/challenge/manage-challenge/html-preview/html-preview.component';
import { EvaluateChallengeInstanceComponent } from './component/challenge/manage-challenge/evaluate-challenge-instance/evaluate-challenge-instance.component';
import { ViewSubmissionComponent } from './component/challenge/manage-challenge/evaluate-challenge-instance/view-submission/view-submission.component';
import { AgGridRowNumberComponent } from './component/challenge/challenge-details/ag-grid-row-number/ag-grid-row-number.component';
import { TimerComponent } from './component/challenge/live-challenge/timer/timer.component';
import { CdTimerModule } from 'angular-cd-timer';
import { TimeTakenPipe } from './pipe/time-taken-pipe';


const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};

@NgModule({
  declarations: [    
    ChallengeComponent,
    ViewChallengeComponent,
    ChallengeWidgetComponent,
    ChallengeDetailsComponent,
    LiveChallengeComponent,
    CodeEditorComponent,    
    ChallengeInstructionComponent,
    CompileResultComponent,
    CustomInputComponent,
    TestResultComponent,
    FreeTextEditorComponent,        
    ManageChallengeComponent, 
    ManageChallengeInstanceComponent, 
    ManageProblemComponent, 
    CreateProblemComponent, 
    CreateChallengeInstanceComponent, 
    CreateChallengeComponent, 
    HtmlPreviewComponent, 
    EvaluateChallengeInstanceComponent, 
    ViewSubmissionComponent, 
    AgGridRowNumberComponent, 
    TimerComponent,
    TimeTakenPipe    
  ],
  imports: [    
    CommonModule,
    AppCommonModule,        
    ChallengeRoutingModule,
    BsDatepickerModule.forRoot(),
    ReactiveFormsModule,
    AgGridModule.withComponents([]),    
    PerfectScrollbarModule,
    NgbModule,    
    AngularSplitModule,
    FormsModule,
    MonacoEditorModule.forRoot(CodeEditorConfig), // use forRoot() in main app module only.      
    SweetAlert2Module.forRoot(),    
    ModalModule.forRoot(),
    AccordionModule.forRoot(),   
    CdTimerModule    
  ],
  providers: [
    {
      provide: PERFECT_SCROLLBAR_CONFIG,
      useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG,
    },
    TimeTakenPipe    
  ]  
})
export class ChallengeModule { }
