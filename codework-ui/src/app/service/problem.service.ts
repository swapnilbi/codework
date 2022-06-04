import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map,tap } from 'rxjs/operators';
import { AppConfig } from 'src/app/common/app.config';
import { HttpHelper } from 'src/app/common/utility/utility';
import { Response } from 'src/app/model/response.model';
import { Language, Problem } from '../model/problem.model';

@Injectable({
  providedIn: 'root'
})
export class ProblemService {

  constructor(private httpClient : HttpClient) { }

  public getProblems(instanceId : number): Observable<Array<Problem>>{    
    let queryParams: any = {
      'challengeInstanceId' : instanceId
    } 
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.GET_PROBLEMS_URL,queryParams);    
    return this.httpClient.get<Response<Array<Problem>>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public getProblem(problemId : number): Observable<Problem>{    
    let queryParams: any = {
      'problemId' : problemId
    } 
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.GET_PROBLEM_URL,queryParams);    
    return this.httpClient.get<Response<Problem>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }


  public deleteProblem(problemId : number): Observable<any>{    
    let queryParams: any = {
      'problemId' : problemId
    } 
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.DELETE_PROBLEM_URL,queryParams);    
    return this.httpClient.delete<Response<any>>(serviceUrl)
    .pipe(
      map((data) => {
        return data;
      }),
      tap(event => {})
    );
  }

  public createProblem(problem : Problem): Observable<Problem>{        
    const serviceUrl = AppConfig.SERVICE_URL.CREATE_PROBLEM_URL;    
    return this.httpClient.post<Response<Problem>>(serviceUrl,problem)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public updateProblem(problem : Problem): Observable<Problem>{   
    let queryParams: any = {
      'problemId' : problem.id
    } 
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.UPDATE_PROBLEM_URL,queryParams);         
    return this.httpClient.put<Response<Problem>>(serviceUrl,problem)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public getLanguages(): Observable<Array<Language>>{        
    const serviceUrl = AppConfig.SERVICE_URL.GET_LANGUAGES_URL;    
    return this.httpClient.get<Response<Array<Language>>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  


}
