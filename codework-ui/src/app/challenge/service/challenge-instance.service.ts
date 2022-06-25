import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map,tap } from 'rxjs/operators';
import { AppConfig } from 'src/app/common/app.config';
import { HttpHelper } from 'src/app/common/utility/utility';
import { ChallengeInstance } from 'src/app/challenge/model/challenge-instance.model';
import { Response } from 'src/app/challenge/model/response.model';
import { UserSubmission } from '../model/user-submission.model';
import { EvaluateProblem } from '../model/evaluate-problem.model';
import { ProblemSolution } from '../model/problem-solution.model';

@Injectable({
  providedIn: 'root'
})
export class ChallengInstanceService {

  constructor(private httpClient : HttpClient) { }

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

  public createChallengeInstance(challengeInstance : ChallengeInstance): Observable<ChallengeInstance>{            
    const serviceUrl = AppConfig.SERVICE_URL.CREATE_CHALLENGE_INTANCE_URL;
    return this.httpClient.post<Response<ChallengeInstance>>(serviceUrl,challengeInstance)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public getChallengeInstance(instanceId : number): Observable<ChallengeInstance>{  
    let queryParams: any = {
      'challengeInstanceId' : instanceId
    }    
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.GET_CHALLENGE_INTANCE_URL,queryParams);                  
    return this.httpClient.get<Response<ChallengeInstance>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public deleteChallengeInstance(challengeInstanceId : number): Observable<any>{            
    let queryParams: any = {
      'challengeInstanceId' : challengeInstanceId
    }    
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.DELETE_CHALLENGE_INTANCE_URL,queryParams);    
    return this.httpClient.delete<Response<any>>(serviceUrl)
    .pipe(
      map((data) => {
        return data;
      }),
      tap(event => {})
    );
  }

  public updateChallengeInstance(challengeInstance : ChallengeInstance): Observable<ChallengeInstance>{  
    let queryParams: any = {
      'challengeInstanceId' : challengeInstance.id
    }    
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.UPDATE_CHALLENGE_INTANCE_URL,queryParams);                  
    return this.httpClient.put<Response<ChallengeInstance>>(serviceUrl,challengeInstance)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public startChallengeInstance(challengeInstanceId : number): Observable<ChallengeInstance>{        
    let queryParams: any = {
      'challengeInstanceId' : challengeInstanceId
    }     
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.PUBLISH_CHALLENGE_INTANCE_URL,queryParams);
    return this.httpClient.get<Response<ChallengeInstance>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public stopChallengeInstance(challengeInstanceId : number): Observable<ChallengeInstance>{        
    let queryParams: any = {
      'challengeInstanceId' : challengeInstanceId
    }     
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.STOP_CHALLENGE_INTANCE_URL,queryParams);
    return this.httpClient.get<Response<ChallengeInstance>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public getChallengeInstanceSubmissions(challengeInstanceId : number): Observable<Array<UserSubmission>>{        
    let queryParams: any = {
      'challengeInstanceId' : challengeInstanceId
    }     
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.GET_CHALLENGE_INTANCE_SUBMISSIONS_URL,queryParams);
    return this.httpClient.get<Response<Array<UserSubmission>>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public getSubmittedProblems(challengeInstanceSubmissionId : number): Observable<Array<EvaluateProblem>>{        
    let queryParams: any = {
      'challengeInstanceSubmissionId' : challengeInstanceSubmissionId
    }     
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.GET_SUBMITTED_PROBLEMS_URL,queryParams);
    return this.httpClient.get<Response<Array<EvaluateProblem>>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public getUserSubmittedProblems(challengeInstanceSubmissionId : number): Observable<Array<EvaluateProblem>>{        
    let queryParams: any = {
      'challengeInstanceSubmissionId' : challengeInstanceSubmissionId
    }     
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.GET_USER_SUBMITTED_PROBLEMS_URL,queryParams);
    return this.httpClient.get<Response<Array<EvaluateProblem>>>(serviceUrl)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public updateProblemSolution(problemSolution : ProblemSolution): Observable<UserSubmission>{        
    let queryParams: any = {
      'problemSubmissionId' : problemSolution.id
    }     
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.UPDATE_PROBLEM_SUBMISSION_URL,queryParams);
    return this.httpClient.post<Response<UserSubmission>>(serviceUrl,problemSolution)
    .pipe(
      map((data) => {
        return data.data;
      }),
      tap(event => {})
    );
  }

  public checkSubmissionResult(challengeInstanceId : number): Observable<Response<any>>{        
    let queryParams: any = {
      'challengeInstanceId' : challengeInstanceId
    }     
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.EVALUATE_CHALLENGE_INTANCE_SUBMISSIONS_URL,queryParams);
    return this.httpClient.post<Response<any>>(serviceUrl,null)
    .pipe(
      map((data) => {
        return data;
      }),
      tap(event => {})
    );
  }

}
