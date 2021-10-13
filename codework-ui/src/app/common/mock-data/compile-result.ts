import { ProblemSolutionResult } from "src/app/model/problem-solution-result.model";
import { Response } from "src/app/model/response.model";

let compileResultObj : ProblemSolutionResult = {
    result: false,
    compilationStatus : true,
    compilationLog : "asdasdasd sdasd",
    standardOutput : "",    
    testCaseResults: [
        {
            id : 1,
            name : "Test Case 1",
            input : "10",
            expectedOutput : "20",
            actualOutput : "30",
            time : 1,
            memory: 5,                        
            status : true
        },
        {
            id : 2,
            name : "Test Case 2",
            input : "10",
            expectedOutput : "40",
            actualOutput : "65",
            time : 1,
            memory: 5,                        
            status : false
        }
    ]
}

let compileResult : Response<ProblemSolutionResult> = {
    data : compileResultObj
}

export { compileResult }


let runAllTestsResultObj : ProblemSolutionResult = {
    result: false,
    compilationStatus : true,
    compilationLog : "asdasdasd sdasd",
    standardOutput : "",  
    timeLimit : 5,  
    memoryLimit : 10,  
    testCaseResults: [
        {
            id : 1,
            name : "Test Case 1",
            input : "10",            
            time : 1,
            memory: 5,                        
            status : true
        },
        {
            id : 2,
            name : "Test Case 2",
            input : "10",            
            time : 1,
            memory: 5,                        
            status : false,
            remark : "Time limit exceeded"
        },
        {
            id : 1,
            name : "Test Case 1",
            input : "10",            
            time : 1,
            memory: 5,                        
            status : true
        },
        {
            id : 2,
            name : "Test Case 2",
            input : "10",            
            time : 1,
            memory: 5,                        
            status : false,
            remark : "Memory limit exceeded"
        },
        {
            id : 1,
            name : "Test Case 1",
            input : "10",            
            time : 1,
            memory: 5,                        
            status : true
        },
        {
            id : 2,
            name : "Test Case 2",
            input : "10",            
            time : 1,
            memory: 5,                        
            status : false
        },
        {
            id : 1,
            name : "Test Case 1",
            input : "10",            
            time : 1,
            memory: 5,                        
            status : true
        },
        {
            id : 2,
            name : "Test Case 2",
            input : "10",            
            time : 1,
            memory: 5,                        
            status : false
        },
        {
            id : 1,
            name : "Test Case 1",
            input : "10",            
            time : 1,
            memory: 5,                        
            status : true
        },
        {
            id : 2,
            name : "Test Case 2",
            input : "10",            
            time : 1,
            memory: 5,                        
            status : false
        }
    ]
}

let runAllTestsResult : Response<ProblemSolutionResult> = {
    data : runAllTestsResultObj
}

export { runAllTestsResult }
