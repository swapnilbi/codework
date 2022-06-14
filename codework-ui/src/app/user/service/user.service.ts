import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { AppConfig } from 'src/app/common/app.config';
import { Response } from 'src/app/challenge/model/response.model';
import { User } from '../model/user.model';
import { HttpHelper } from 'src/app/common/utility/utility';
import { ChangePasswordModel } from '../model/change-password.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient : HttpClient) { }

  public getUsers(): Observable<Array<User>>{        
    const serviceUrl = AppConfig.SERVICE_URL.GET_USERS_LIST_URL;
    return this.httpClient.get<Response<Array<User>>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public enableUser(userId : number): Observable<User>{    
    let queryParams: any = {
      'userId' : userId
    }     
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.ENABLE_USER_URL,queryParams);        
    return this.httpClient.get<Response<User>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public changePassword(changePasswordInput : ChangePasswordModel): Observable<any>{        
    const serviceUrl = AppConfig.SERVICE_URL.CHANGE_USER_PASSWORD_URL;        
    return this.httpClient.put<Response<any>>(serviceUrl, changePasswordInput)
    .pipe(
      map((data) => {
        return data;
      }),
      tap(event => {})
    );
  }

  public disableUser(userId : number): Observable<User>{    
    let queryParams: any = {
      'userId' : userId
    }     
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.DISABLE_USER_URL,queryParams);        
    return this.httpClient.get<Response<User>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }


}
