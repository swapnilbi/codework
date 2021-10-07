import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map,tap } from 'rxjs/operators';
import { AppConfig } from 'src/app/common/app.config';
import { HttpHelper } from 'src/app/common/utility/utility';
import { Challenge } from 'src/app/model/challenge.model';

export class Alert {
  id?: string;
  type?: AlertType;
  message?: string;  
  keepAfterRouteChange?: boolean;
  fade?: boolean;
  title?:string;

  constructor(init?:Partial<Alert>) {
      Object.assign(this, init);
  }
}

export enum AlertType {
  Success = "success",
  Error = "error",
  Info = "info",
  Warning = "warning"
}