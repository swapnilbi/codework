import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FakeBackendInterceptor } from './common/interceptors/mock-http-interceptor';
import { HttpRequestInterceptor } from './common/interceptors/http-interceptor';
import { environment } from 'src/environments/environment';
import { ChallengeModule } from './challenge/challenge.module';
import { UserModule } from './user/user.module';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppCommonModule } from './common/common.module';
import { LoaderService } from './common/component/common/loader/loader.service';

export const isMock = environment.mock;

@NgModule({
  declarations: [
    AppComponent       
  ],
  imports: [    
    AppCommonModule,
    BrowserModule,
    BrowserAnimationsModule,        
    AppRoutingModule,
    ChallengeModule,
    UserModule,
    HttpClientModule 
  ],
  providers: [      
    {
      provide: HTTP_INTERCEPTORS,
      useClass: isMock ? FakeBackendInterceptor : HttpRequestInterceptor,
      multi: true
    }        
],
  bootstrap: [AppComponent]
})
export class AppModule { }
