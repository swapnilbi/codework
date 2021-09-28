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
import { FormsModule } from '@angular/forms';

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
    CodeEditorComponent        
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    CommonModule,    
    PerfectScrollbarModule,
    AngularSplitModule,
    FormsModule,
    MonacoEditorModule.forRoot(CodeEditorConfig) // use forRoot() in main app module only.    
  ],
  providers: [{
    provide: PERFECT_SCROLLBAR_CONFIG,
    useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
