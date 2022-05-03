import { ProblemSolution } from "./problem-solution.model";

export interface ChallengeSubmitInput {
    challengeInstanceId? : number,
    solutionList : Array<ProblemSolution>
}
