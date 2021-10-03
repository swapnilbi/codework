import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { AppConfig } from 'src/app/common/app.config';
import { HttpHelper } from 'src/app/common/utility/utility';

@Injectable({
  providedIn: 'root'
})
export class LiveChallengeService {

  constructor(private httpClient : HttpClient) { }

  public getProblems(challengeId : number): Observable<Array<any>>{    
    let queryParams: any = {
      'challengeId' : challengeId
    } 
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.GET_PROBLEMS_URL,queryParams);
    return this.httpClient.get<Array<any>>(serviceUrl)
    .pipe(
      map((data) => {
        return data;
      }),
      tap(event => {})
    );
  }



}
