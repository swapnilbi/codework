export interface Response<T> {
    data : T,
    remarks? : Array<Remark>,       
}

export interface Remark {
     message : string,
     type: RemarkType
  }

export enum RemarkType {
    INFO = "INFO",
    WARNING = "WARNING",
    ERROR = "ERROR",
}