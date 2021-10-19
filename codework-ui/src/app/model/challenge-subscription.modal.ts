export interface ChallengeSubscription {
    id : number, 
    challengeId : number,
    userId : string,
    status : ChallengeSubscriptionStatus,
    startDate? : Date,
    endDate? : Date,
    createdAt? : Date     
}

export enum ChallengeSubscriptionStatus {
    REGISTERED = "REGISTERED", 
    CANCELED = "CANCELED", 
    STARTED = "STARTED", 
    SUBMITTED = "SUBMITTED"
}
