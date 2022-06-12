import { ProblemSolution } from "./problem-solution.model";
import { Language, ProblemType } from "./problem.model";

export interface EvaluateProblem {
    problemSolution : ProblemSolution,
    name? : String,
    type? : ProblemType,
    language : Language
}
