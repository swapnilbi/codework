import { ChallengeInstance } from "./challenge-instance.model";
import { Challenge } from "./challenge.model";
import { ProblemType } from "./problem.model";
import { UserSubmission } from "./user-submission.model";

export interface LiveChallenge {
    challengeDetails : Challenge,
    challengeInstance : ChallengeInstance,
    challengeInstanceSubmission : UserSubmission,
    problemTypes? : Array<ProblemType>    
}
