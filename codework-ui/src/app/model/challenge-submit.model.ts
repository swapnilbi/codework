import { ProblemSolution } from "./problem-solution.model";

export interface ChallengeSubmitInput {
    challengeId : number,
    solutions : Array<ProblemSolution>
}
