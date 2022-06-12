import { ProblemSolution } from "./problem-solution.model";

export interface ChallengeSubmitInput {
    challengeInstanceId? : number,
    challengeInstanceSubmissionId? : number;
    solutionList : Array<ProblemSolution>
}
