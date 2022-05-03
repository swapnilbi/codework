export interface ProblemSolution {
    challengeId : number,
    challengeInstanceId? : number,
    problemId : number,
    solution : string,
    customInput? : string | null,
    languageId? : number,
    submitted? : boolean
}
