import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SidebarComponent } from './component/common/sidebar/sidebar.component';

import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { PERFECT_SCROLLBAR_CONFIG } from 'ngx-perfect-scrollbar';
import { PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';
import { CommonModule } from '@angular/common';
import { ViewChallengeComponent } from './component/challenge/view-challenge/view-challenge.component';
import { ChallengeWidgetComponent } from './component/challenge/challenge-widget/challenge-widget.component';
import { ChallengeDetailsComponent } from './component/challenge/challenge-details/challenge-details.component';
import { LiveChallengeComponent } from './component/challenge/live-challenge/live-challenge.component';
import { AngularSplitModule } from 'angular-split';
import { MonacoEditorModule } from 'ngx-monaco-editor';
import { CodeEditorComponent } from './component/challenge/code-editor/code-editor.component';
import { CodeEditorConfig } from './component/challenge/code-editor/code-editor.config';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FakeBackendInterceptor } from './common/interceptors/mock-http-interceptor';
import { HttpRequestInterceptor } from './common/interceptors/http-interceptor';
import { environment } from 'src/environments/environment';
import { AlertComponent } from './component/common/alert/alert.component';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { LoaderComponent } from './component/common/loader/loader.component';
import { LoaderService } from './component/common/loader/loader.service';
import { ChallengeInstructionComponent } from './component/challenge/live-challenge/challenge-instruction/challenge-instruction.component';
import { CompileResultComponent } from './component/challenge/live-challenge/compile-result/compile-result.component';
import { ModalModule } from 'ngx-bootstrap/modal';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { CustomInputComponent } from './component/challenge/live-challenge/custom-input/custom-input.component';
import { TestResultComponent } from './component/challenge/live-challenge/test-result/test-result.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { FreeTextEditorComponent } from './component/challenge/free-text-editor/free-text-editor.component';
import { LoginComponent } from './component/authentication/login/login.component';
import { LocalStorageService } from './common/utility/local-storage';
import { ManageChallengeComponent } from './component/challenge/manage-challenge/manage-challenge.component';
import { AgGridModule } from 'ag-grid-angular';
import { ManageChallengeInstanceComponent } from './component/challenge/manage-challenge/manage-challenge-instance/manage-challenge-instance.component';
import { ManageProblemComponent } from './component/challenge/manage-challenge/manage-problem/manage-problem.component';
import { CreateProblemComponent } from './component/challenge/manage-challenge/create-problem/create-problem.component';
import { CreateChallengeInstanceComponent } from './component/challenge/manage-challenge/create-challenge-instance/create-challenge-instance.component';
import { CreateChallengeComponent } from './component/challenge/manage-challenge/create-challenge/create-challenge.component';

export const isMock = environment.mock;

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    ViewChallengeComponent,
    ChallengeWidgetComponent,
    ChallengeDetailsComponent,
    LiveChallengeComponent,
    CodeEditorComponent,
    AlertComponent,
    LoaderComponent,
    ChallengeInstructionComponent,
    CompileResultComponent,
    CustomInputComponent,
    TestResultComponent,
    FreeTextEditorComponent,    
    LoginComponent, 
    ManageChallengeComponent, 
    ManageChallengeInstanceComponent, 
    ManageProblemComponent, CreateProblemComponent, CreateChallengeInstanceComponent, CreateChallengeComponent    
  ],
  imports: [
    BrowserModule,
    FormsModule,    
    BsDatepickerModule.forRoot(),
    ReactiveFormsModule,
    AgGridModule.withComponents([]),
    BrowserAnimationsModule,
    AppRoutingModule,
    CommonModule,    
    PerfectScrollbarModule,
    NgbModule,
    AngularSplitModule,
    FormsModule,
    MonacoEditorModule.forRoot(CodeEditorConfig), // use forRoot() in main app module only.  
    HttpClientModule,
    SweetAlert2Module.forRoot(),
    MatProgressSpinnerModule,
    ModalModule.forRoot(),
    AccordionModule.forRoot(),        
  ],
  providers: [
    {
      provide: PERFECT_SCROLLBAR_CONFIG,
      useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG,
    },    
    {
      provide: HTTP_INTERCEPTORS,
      useClass: isMock ? FakeBackendInterceptor : HttpRequestInterceptor,
      multi: true
    },
    LoaderService,
    LocalStorageService
],
  bootstrap: [AppComponent]
})
export class AppModule { }
