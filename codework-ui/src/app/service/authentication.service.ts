import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { AppConfig } from 'src/app/common/app.config';
import { Response } from 'src/app/model/response.model';
import { LoginResponse } from 'src/app/model/user-login-response.model';
import { UserLogin } from 'src/app/model/user-login.model';
import { UserProfile } from 'src/app/model/user-profile.model';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient : HttpClient) { }

  public authenticate(loginInput : UserLogin): Observable<LoginResponse>{        
    const serviceUrl = AppConfig.SERVICE_URL.AUTHENTICATE_URL;
    return this.httpClient.post<Response<LoginResponse>>(serviceUrl,loginInput)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public logout(): Observable<any>{        
    const serviceUrl = AppConfig.SERVICE_URL.LOGOUT_URL;
    return this.httpClient.get<any>(serviceUrl)
    .pipe(
      map((data) => {
        return data;
      }),
      tap(event => {})
    );
  }

  public getUserProfile(): Observable<UserProfile>{        
    const serviceUrl = AppConfig.SERVICE_URL.GET_USER_PROFILE_URL;
    return this.httpClient.get<Response<UserProfile>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

}
