import { Injectable } from '@angular/core';
import { HttpRequest, HttpResponse, HttpHandler, HttpEvent, HttpInterceptor, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { delay, mergeMap, materialize, dematerialize } from 'rxjs/operators';
import { Challenge, ChallengeStatus } from '../../model/challenge.model';
import { challengeList } from '../mock-data/challenges';
import { Problem } from 'src/app/model/problem.model';
import { problemList } from '../mock-data/problems';

// array in local storage for registered users
let challenges : Array<Challenge> = challengeList;
let problems : Array<Problem> = problemList;

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
                case url.endsWith('/api/challenges'):
                    return getChallenges();
                case url.match(/\/api\/challenge\/\d+$/) && true:                    
                    return getChallengeById(getIdFromUrl(url,1));
                case url.match(/\/api\/challenge\/\d+\/register$/) && true:
                    return registerChallenge(getIdFromUrl(url,2));                
                case url.match(/\/api\/challenge\/\d+\/problems$/) && true:
                    return getProblems(getIdFromUrl(url,2));                
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

        function getChallengeById(challengeId : number) {            
            const challenge = challenges.find(x => x.id === challengeId);            
            return ok(challenge);
        }

        function getIdFromUrl(url : string, urlLocation : number) {            
            let urlParts = url.split('/');            
            return parseInt(urlParts[urlParts.length - urlLocation]);      
        }

        function registerChallenge(challengeId : number) {                
            let challenge = challenges.find(x => x.id === challengeId);
            if(challenge){                                
                challenge.isRegistered = true;                   
            }                                    
            return ok(challenge);
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