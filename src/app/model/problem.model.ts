export interface Problem {
    id : number,
    name : string,
    problemStatement : string,    
    type : ProblemType,    
    languagesAllowed? : Array<Language>,
    startDate : Date,
    endDate : Date,    
    isSubmitted : boolean,
    createdAt? : Date,
    placeHolderSolution? : string,
    solution?: string,
    noOfTestCases?: number,
    sampleTestCase? : TestCase
}

export interface TestCase {
    id? : number,
    input : string,
    expectedOutput : string
}

export enum ProblemType {
    PROGRAM,
    PUZZLE,
    QUIZ
}

export interface Language {    
    id : string,
    description : string,
}