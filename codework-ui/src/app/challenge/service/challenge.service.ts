import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map,tap } from 'rxjs/operators';
import { AppConfig } from 'src/app/common/app.config';
import { HttpHelper } from 'src/app/common/utility/utility';
import { ChallengeInstance } from 'src/app/challenge/model/challenge-instance.model';
import { ChallengeSubscription } from 'src/app/challenge/model/challenge-subscription.modal';
import { Challenge } from 'src/app/challenge/model/challenge.model';
import { LiveChallenge } from 'src/app/challenge/model/live-challenge.model';
import { Response } from 'src/app/challenge/model/response.model';
import { ChallengeLeaderboard } from '../model/challenge-leaderboard';

@Injectable({
  providedIn: 'root'
})
export class ChallengeService {

  constructor(private httpClient : HttpClient) { }

  public getActiveChallenges(): Observable<Array<Challenge>>{
    
    const serviceUrl = AppConfig.SERVICE_URL.GET_CHALLENGE_URL;
    return this.httpClient.get<Response<Array<Challenge>>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public getChallengeList(): Observable<Array<Challenge>>{
    
    const serviceUrl = AppConfig.SERVICE_URL.GET_CHALLENGE_LIST_URL;
    return this.httpClient.get<Response<Array<Challenge>>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public getChallengeInstanceList(challengeId : Number): Observable<Array<ChallengeInstance>>{
    let queryParams: any = {
      'challengeId' : challengeId
    }    
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.GET_CHALLENGE_INSTANCE_LIST_URL,queryParams);    
    return this.httpClient.get<Response<Array<ChallengeInstance>>>(serviceUrl)
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
    return this.httpClient.get<Response<Challenge>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  
  public updateChallenge( challenge : Challenge): Observable<Challenge>{
    let queryParams: any = {
      'challengeId' : challenge.id
    }    
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.UPDATE_CHALLENGE_URL,queryParams);
    return this.httpClient.put<Response<Challenge>>(serviceUrl,challenge)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public deleteChallenge( challengeId : number): Observable<any>{
    let queryParams: any = {
      'challengeId' : challengeId
    }    
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.DELETE_CHALLENGE_URL,queryParams);
    return this.httpClient.delete<Response<any>>(serviceUrl)
    .pipe(
      map((data) => {
        return data;
      }),
      tap(event => {})
    );
  }

  public createChallenge( challenge : Challenge): Observable<Challenge>{    
    const serviceUrl = AppConfig.SERVICE_URL.CREATE_CHALLENGE_URL;
    return this.httpClient.post<Response<Challenge>>(serviceUrl,challenge)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public getLiveChallengeDetails( challengeInstanceId : Number): Observable<LiveChallenge>{
    let queryParams: any = {
      'challengeInstanceId' : challengeInstanceId
    }    
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.GET_LIVE_CHALLENGE_DETAILS_URL,queryParams);
    return this.httpClient.get<Response<LiveChallenge>>(serviceUrl)
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
    return this.httpClient.get<Response<Challenge>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public publishChallenge(challengeId : number): Observable<Challenge>{        
    let queryParams: any = {
      'challengeId' : challengeId
    }     
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.PUBLISH_CHALLENGE_URL,queryParams);
    return this.httpClient.get<Response<Challenge>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public stopChallenge(challengeId : number): Observable<Challenge>{        
    let queryParams: any = {
      'challengeId' : challengeId
    }     
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.STOP_CHALLENGE_URL,queryParams);
    return this.httpClient.get<Response<Challenge>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public getChallengeLeaderboard( challengeId : Number): Observable<ChallengeLeaderboard>{
    let queryParams: any = {
      'challengeId' : challengeId
    }    
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.GET_CHALLENGE_LEADERBOARD_URL,queryParams);
    return this.httpClient.get<Response<ChallengeLeaderboard>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

}
