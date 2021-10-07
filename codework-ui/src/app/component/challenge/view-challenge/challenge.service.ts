import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map,tap } from 'rxjs/operators';
import { AppConfig } from 'src/app/common/app.config';
import { HttpHelper } from 'src/app/common/utility/utility';
import { Challenge } from 'src/app/model/challenge.model';
import { Response } from 'src/app/model/response.model';

@Injectable({
  providedIn: 'root'
})
export class ChallengeService {

  constructor(private httpClient : HttpClient) { }

  public getActiveChallenges(): Observable<Array<Challenge>>{
    
    const serviceUrl = AppConfig.SERVICE_URL.GET_CHALLENGE_URL;
    return this.httpClient.get<Response>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public getChallengeDetails( challengeId : Number): Observable<Challenge>{
    let queryParams: any = {
      'challengeId' : challengeId
    }    
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.GET_CHALLENGE_DETAILS_URL,queryParams);
    return this.httpClient.get<Response>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

    
  public registerChallenge( challengeId : Number): Observable<Challenge>{    
    let queryParams: any = {
      'challengeId' : challengeId
    }        
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.REGISTER_CHALLENGE_URL,queryParams);
    return this.httpClient.get<Response>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }


}
