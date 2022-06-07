import { ChallengeSubscription } from "./challenge-subscription.modal";
import { UserSubmission } from "./user-submission.model";

export interface Challenge {
    id : number,
    name : string,
    shortDescription : string,
    longDescription : string,
    type? : string,    
    languagesAllowed? : string[],
    startDate : Date,
    endDate : Date,
    status : ChallengeStatus,
    challengeSubscription? : ChallengeSubscription,
    userSubmissions? : Array<UserSubmission>,
    bannerImage? : string,
    createdAt? : Date     
}

export enum ChallengeStatus {
    SCHEDULED = "SCHEDULED",
    LIVE = 'LIVE',
    EXPIRED = "EXPIRED"
  }

export enum ParticipationStatus {
    NOT_STARTED = "NOT_STARTED",
    STARTED = "STARTED",
    FINISHED = "FINISHED",
}