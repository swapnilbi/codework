export interface UserSubmission {
    id? : number,
    challengeId : number,
    challengeInstanceId : number,
    problem : string,
    userId? : number,
    problemType? : string,
    startTime? : Date,
    endTime? : Date
    submissionStatus : SubmissionStatus,
    submittedTime? : Date | null
}


export enum SubmissionStatus {
    NOT_STARTED = "NOT_STARTED",
    LIVE = "LIVE",
    IN_PROGRESS = "IN_PROGRESS",
    SUBMITTED = "SUBMITTED",
    EXPIRED = "EXPIRED"
}