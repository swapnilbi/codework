import { UserProfile } from "src/app/authentication/model/user-profile.model";
import { ChallengeInstance } from "./challenge-instance.model";

export interface ChallengeLeaderboard {
    challengeInstanceList : Array<ChallengeInstance>
	userSubmissionsList : Array<UserSubmissions>
}

export interface UserSubmissions {
	id : number,
	userDetails : UserProfile,
	challengeInstancePoints : Array<ChallengeInstancePoints>
}

export interface ChallengeInstancePoints {
	challengeInstanceId : number,
	userId : number,
	points : number,
	timeTaken : number
}