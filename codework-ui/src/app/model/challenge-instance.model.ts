export interface ChallengeInstance {
    id : number,
	challengeId : number,
	name : string,
	type : string,
	startDate : Date,
	endDate : Date,
	instanceStatus : ChallengeInstanceStatus,
	createdAt : Date,
	createdBy : number
}

export enum ChallengeInstanceStatus {
    CREATED = "CREATED",
    LIVE = 'LIVE',
    EXPIRED = "EXPIRED"    
  }
