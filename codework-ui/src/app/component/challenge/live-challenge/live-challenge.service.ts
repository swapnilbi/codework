import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { AppConfig } from 'src/app/common/app.config';
import { HttpHelper } from 'src/app/common/utility/utility';
import { ProblemSolutionResult } from 'src/app/model/problem-solution-result.model';
import { ProblemSolution } from 'src/app/model/problem-solution.model';
import { Problem } from 'src/app/model/problem.model';

@Injectable({
  providedIn: 'root'
})
export class LiveChallengeService {
  
  constructor(private httpClient : HttpClient) { }

  public getProblems(challengeId : number): Observable<Array<Problem>>{    
    let queryParams: any = {
      'challengeId' : challengeId
    } 
    const serviceUrl = HttpHelper.getUrl(AppConfig.SERVICE_URL.GET_PROBLEMS_URL,queryParams);
    return this.httpClient.get<Array<Problem>>(serviceUrl)
    .pipe(
      map((data) => {
        return data;
      }),
      tap(event => {})
    );
  }

  public compileSolution(problemSolution : ProblemSolution): Observable<ProblemSolutionResult>{        
    const serviceUrl = AppConfig.SERVICE_URL.COMPILE_SOLUTION_URL;
    return this.httpClient.post<ProblemSolutionResult>(serviceUrl,problemSolution)
    .pipe(
      map((data) => {
        return data;
      }),
      tap(event => {})
    );
  }

  public runAllTests(problemSolution : ProblemSolution): Observable<ProblemSolutionResult>{        
    const serviceUrl = AppConfig.SERVICE_URL.RUN_ALL_TEST_CASES_URL;
    return this.httpClient.post<ProblemSolutionResult>(serviceUrl,problemSolution)
    .pipe(
      map((data) => {
        return data;
      }),
      tap(event => {})
    );
  }

  public saveProblemSolution(problemSolution : ProblemSolution): Observable<boolean>{        
    const serviceUrl = AppConfig.SERVICE_URL.SAVE_SOLUTION_URL;
    return this.httpClient.post<boolean>(serviceUrl,problemSolution)
    .pipe(
      map((data) => {
        return data;
      }),
      tap(event => {})
    );
  }
  
}
