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