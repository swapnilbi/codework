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
    isRegistered : boolean,
    participationStatus : ParticipationStatus,
    bannerImage? : string,
    createdAt? : Date     
}

export enum ChallengeStatus {
    SCHEDULED,
    LIVE,
    EXPIRED
  }

export enum ParticipationStatus {
    NOT_STARTED,
    STARTED,
    FINISHED,
}