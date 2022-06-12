import { TestCaseResult } from "./problem-solution-result.model"

export interface ProblemSolution {
    id? : number,
    challengeId? : number,
    challengeInstanceId? : number,
    challengeInstanceSubmissionId? : number,
    evaluationStatus? : string,
    problemId? : number,
    points? : number,
    timeTaken? : number,
    avgExecutionTime? : number,
    solution? : string,
    testCaseResults? : Array<TestCaseResult>,
    solutionResult? : string,
    customInput? : string | null,
    languageId? : number,
    evaluationRemarks? : string,
    submitted? : boolean
}
