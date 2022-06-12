import { UserProfile } from 'src/app/user/model/user-profile.model';

export interface UserSubmission {
    id : number,
    challengeId : number,
    challengeInstanceId : number,
    problem : string,
    userId? : number,
    problemType? : string,
    startTime? : Date,
    endTime? : Date
    submissionStatus : SubmissionStatus,
    submittedTime? : Date | null,
    evaluationStatus? : EvaluationStatus,
	userDetails? : UserProfile,
	totalPoints? : number,
	timeTaken? : number
}


export enum SubmissionStatus {
    NOT_STARTED = "NOT_STARTED",
    LIVE = "LIVE",
    IN_PROGRESS = "IN_PROGRESS",
    SUBMITTED = "SUBMITTED",
    EXPIRED = "EXPIRED"
}


export enum EvaluationStatus {
    NOT_STARTED = "NOT_STARTED",
    COMPLETED = "COMPLETED",
    IN_PROGRESS = "IN_PROGRESS",
    FAILED = "FAILED"
}
