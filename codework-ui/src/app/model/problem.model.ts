import { ProblemSolution } from "./problem-solution.model";

export interface Problem {
    id : number,
    challengeId : number,
    name : string,    
    problemStatement : string,    
    type : ProblemType,    
    languagesAllowed? : Array<Language>,
    startDate : Date,
    endDate : Date,    
    memoryLimit? : number,
    cpuLimit?: number,
    isSubmitted? : boolean,
    createdAt? : Date,
    placeHolderSolution? : Record<number,string>,
    testCases?: Array<TestCase>,
    problemSolution?: ProblemSolution    
}

export interface TestCase {
    id : number,
    input : string,
    expectedOutput : string
}

export enum ProblemType {
    PROGRAM = "PROGRAM",
    PUZZLE = "PUZZLE",
    QUIZ = "QUIZ"
}

export interface Language {    
    id : number,
    name : string,
    editorCode : string
}