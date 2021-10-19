
export interface ProblemSolutionResult {
    result : boolean,    
    compilationError : boolean,   
    runTimeError : boolean,   
    compilationLog: string,
    customInput? : string,
    standardOutput?: string,
    timeLimit? : number,
    memoryLimit? : number,
    testCaseResults: Array<TestCaseResult>        
}

export interface TestCaseResult{
    id : number,
    name? : string,
    status : boolean
    input : string,
    time : number,
    memory : number,
    remark? : string,
    expectedOutput? : string,
    actualOutput? : string,    
}