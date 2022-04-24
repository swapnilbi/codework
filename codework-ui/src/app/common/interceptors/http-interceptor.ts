import { Injectable, Injector } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { AlertService } from '../../component/common/alert/alert-service.service';
import { LoaderService } from '../../component/common/loader/loader.service'; 

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {
    constructor(private injector: Injector, private alertService: AlertService, private loaderService: LoaderService) {

    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {               
        //return next.handle(request);
        return next.handle(request).pipe(
            tap(evt => {
                return evt;
            }),
            catchError((err: any) => {
                if(err instanceof HttpErrorResponse) { 
                    if(err.error && err.error.remarks && err.error.remarks.length > 0){
                        this.loaderService.hide();                   
                        this.alertService.error(err.error.remarks[0].message);   
                    }else{
                        this.alertService.error("Something went wrong. Please try again.");   
                    }                                        
                }
                return of(err);
            }));      
    }
}
