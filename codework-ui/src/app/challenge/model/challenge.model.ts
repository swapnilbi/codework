import { ChallengeSubscription } from "./challenge-subscription.modal";
import { ProblemType } from "./problem.model";
import { UserSubmission } from "./user-submission.model";

export interface Challenge {
    id : number,
    name : string,
    shortDescription : string,
    longDescription : string,
    commonInstructions? : string	
    questionSpecificInstructions? : Array<ChallengeInstruction>,    
    type? : string,    
    languagesAllowed? : string[],
    startDate : Date,
    endDate : Date,
    status : ChallengeStatus,
    registrationCount? : number,
    challengeSubscription? : ChallengeSubscription,
    userSubmissions? : Array<UserSubmission>,
    bannerImage? : string,
    createdAt? : Date     
}

export interface ChallengeInstruction{
    problemType : ProblemType,
    instruction : string
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