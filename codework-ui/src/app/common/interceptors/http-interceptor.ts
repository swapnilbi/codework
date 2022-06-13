import { Injectable, Injector } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { AlertService } from '../../common/component/common/alert/alert-service.service';
import { LoaderService } from '../../common/component/common/loader/loader.service'; 
import { UserAuthService } from 'src/app/authentication/service/user-auth.service';
import { Router } from '@angular/router';

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

    constructor(private alertService: AlertService, 
        private router: Router,        
        private userAuthService: UserAuthService, 
        private loaderService: LoaderService) {

    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {               
        //return next.handle(request);
        let token = this.userAuthService.getToken();
        if(token){
            request = request.clone({
                url:  request.url,
                setHeaders: {
                  Authorization: `Bearer ${token}`
                }
            });
        }        
        return next.handle(request).pipe(
            tap(evt => {
                return evt;
            }),
            catchError((err: any) => {                
                this.loaderService.hide();                                   
                if(err instanceof HttpErrorResponse) { 
                    if(err.error && err.status == 401){                                                
                        this.userAuthService.logout();
                        this.router.navigate(['login']);   
                    }else if(err.error && err.status == 403){                                                                        
                        this.router.navigate(['challenges']);   
                    }else if(err.error && err.error.remarks && err.error.remarks.length > 0){                                
                        this.alertService.error(err.error.remarks[0].message);   
                    }else{
                        this.alertService.error("Something went wrong. Please try again.");   
                    }                                        
                }
                return of(err);
            }));      
    }
}
