import { Injectable } from '@angular/core';
import { HttpRequest, HttpResponse, HttpHandler, HttpEvent, HttpInterceptor, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { delay, mergeMap, materialize, dematerialize } from 'rxjs/operators';
import { Challenge, ChallengeStatus } from '../../model/challenge.model';
import { challengeList } from '../mock-data/challenges';
import { Problem } from 'src/app/model/problem.model';
import { problemList } from '../mock-data/problems';
import { compileResult, runAllTestsResult } from '../mock-data/compile-result';
import { Response } from 'src/app/model/response.model';
import { ChallengeSubscriptionStatus } from 'src/app/model/challenge-subscription.modal';

// array in local storage for registered users
let challenges : Response<Array<Challenge>> = challengeList;
let problems : Response<Array<Problem>> = problemList;

@Injectable()
export class FakeBackendInterceptor implements HttpInterceptor {
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const { url, method, headers, body } = request;
        console.log('FakeBackendInterceptor ', url);
        // wrap in delayed observable to simulate server api call
        return of(null)
            .pipe(mergeMap(handleRoute))
            .pipe(materialize())
            .pipe(delay(1000))
            .pipe(dematerialize());

        function handleRoute() {
            switch (true) {
                case url.endsWith('/api/user/authenticate'):
                    return authenticate();
                case url.endsWith('/api/challenge/list'):
                    return getChallenges();
                case url.match(/\/api\/challenge\/\d+$/) && true:
                    return getChallengeById(getIdFromUrl(url,1));
                case url.match(/\/api\/challenge\/\d+\/register$/) && true:
                    return registerChallenge(getIdFromUrl(url,2));
                case url.match(/\/api\/challenge\/\d+\/start$/) && true:
                    return startChallenge(getIdFromUrl(url,2));
                case url.match(/\/api\/challenge\/\d+\/problems$/) && true:
                    return getProblems(getIdFromUrl(url,2));
                case url.endsWith('/api/challenge/solution/compile'):
                    return compileSolution();
                case url.endsWith('/api/challenge/solution/run'):
                    return runAllTests();
                case url.endsWith('/api/challenge/solution/save'):
                    return saveSolution();
                default:
                    // pass through any requests not handled above
                    return next.handle(request);
            }
        }

        // route functions

        function authenticate() {
            return ok({
                id: 100,
                firstName: 'Swapnil',
                lastName: 'Birajdar',
                token: 'fake-token'
            })
        }

        function getChallenges() {            
            return ok(challenges);
        }

        function saveSolution() {
            let saveSolution : Response<boolean> = {
                data : true
            }
            return ok(saveSolution);
        }

        function getChallengeById(challengeId : number) {
            if(challenges.data){
                const challenge = challenges.data.find(x => x.id === challengeId);
                if(challenge){
                    let getChallenge : Response<Challenge> = {
                        data : challenge
                    }
                    return ok(getChallenge);
                }                
            } 
            return ok(null);                       
        }

        function compileSolution() {
            return ok(compileResult)
        }

        function runAllTests() {
            return ok(runAllTestsResult)
        }

        function getIdFromUrl(url : string, urlLocation : number) {
            let urlParts = url.split('/');
            return parseInt(urlParts[urlParts.length - urlLocation]);
        }

        function registerChallenge(challengeId : number) {
            if(challenges.data){
                let challenge = challenges.data.find(x => x.id === challengeId);
                if(challenge){
                    challenge.challengeSubscription = {
                      challengeId :  challenge.id,
                      id : 1,
                      status : ChallengeSubscriptionStatus.REGISTERED,
                      userId : "1"
                    };
                    let getChallenge : Response<Challenge> = {
                        data : challenge
                    }
                    return ok(getChallenge);
                }                
            }            
            return ok(null);
        }

        function startChallenge(challengeId : number) {
            if(challenges.data){
                let challenge = challenges.data.find(x => x.id === challengeId);
                if(challenge){
                    challenge.challengeSubscription = {
                        challengeId :  challenge.id,
                        id : 1,
                        status : ChallengeSubscriptionStatus.REGISTERED,
                        userId : "1"
                    };
                    let getChallenge : Response<Challenge> = {
                        data : challenge
                    }
                    return ok(getChallenge);
                }                
            }            
            return ok(null);
        }

        function getProblems(challengeId : number) {
            return ok(problems);
        }

        // helper functions

        function ok(body? : any) {
            return of(new HttpResponse({ status: 200, body }))
        }

        function error(message? : string) {
            return throwError({ error: { message } });
        }

        function unauthorized() {
            return throwError({ status: 401, error: { message: 'Unauthorised' } });
        }

    }
}
