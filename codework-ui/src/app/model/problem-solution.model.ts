export interface ProblemSolution {
    challengeId : number,
    problemId : number,
    solution : string,
    customInput? : string | null,
    languageId? : number,
    submitted? : boolean
}
