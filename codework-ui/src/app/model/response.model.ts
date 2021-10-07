export interface Response {
    data : any,
    remarks? : Array<Remark>,       
}

export interface Remark {
     message : string,
     type: RemarkType
  }

export enum RemarkType {
    INFO,
    WARNING,
    ERROR,
}