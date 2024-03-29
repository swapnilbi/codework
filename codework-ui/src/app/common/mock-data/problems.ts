import { Problem, ProblemType } from "src/app/challenge/model/problem.model";
import { Response } from "src/app/challenge/model/response.model";

let problems : Array<Problem> = [
    {
        id : 1,
        challengeId : 1,
        challengeInstanceId : 1,
        name : 'Problem 1',        
        problemStatement : "<p> You are given a set of binary elements. You have to eliminate the binary numbers that contain as a substring. The resultant sequence will be 1, 10, 100, 101, 1000, and so on. You are required to generate the code to determine the value of the new sequence. </p> <p> You are required to generate the code to determine the value of the new sequence. </p> <p> <strong>Input format</strong> </p> <ul> <li>First line: <i><b>T</b></i> denoting the number of test cases</li> <li>Next <i><b>T</b></i> lines: A single integer<i><b>K</b></i> </ul> <p> <strong>Output format</strong> </p> <p> Print <i><b>T</b></i> lines representing the code to display the <i><b>K</b></i>th value. </p> <p> <strong>Constraints</strong> </p> <p> 1 <code> < </code> T <code> < </code> 10 </p> <p> 1 <code> < </code> K <code> < </code> 10 </p> <p> <strong>Explanation</strong> </p>",
        isSubmitted : false,
        type : ProblemType.PROGRAM,           
        languagesAllowed : [            
            { id: 1, editorCode : 'java', name: 'Java (openjdk 1.7.0_95)' },              
            { id: 2, editorCode : 'java', name: 'Java 14 (oracle 14)' },
            { id: 3, editorCode : 'python', name: 'Python' },                      
            { id: 4, editorCode : 'c', name: 'C (gcc 5.4.0)' },
            { id: 5, editorCode : 'c', name: 'C++ (g++ 5.4.0)' }            
        ],        
        placeHolderSolution : {
            "1" : "class Simple{\r\n\r\n  public static void main(String args[]){\r\n \t System.out.println(\"Hello Java\"); \r\n  } \r\n\r\n }",
            "2" : "class Simple1{\r\n\r\n  public static void main(String args[]){\r\n \t System.out.println(\"Hello Java\"); \r\n  } \r\n\r\n }"
        },         
        testCases : [{
            id : 1,
            input : "5 5",
            expectedOutput : "10"
        }],     
        startDate : new Date("2021-10-01T00:00:00"),
        endDate : new Date("2021-10-16T00:00:00"),                        
    },
    {
        id : 2,
        challengeId : 1,
        challengeInstanceId : 1,
        name : 'Problem 2',        
        problemStatement : "<p> You are given a set of binary elements. You have to eliminate the binary numbers that contain as a substring. The resultant sequence will be 1, 10, 100, 101, 1000, and so on. You are required to generate the code to determine the value of the new sequence. </p> <p> You are required to generate the code to determine the value of the new sequence. </p> <p> <strong>Input format</strong> </p> <ul> <li>First line: <i><b>T</b></i> denoting the number of test cases</li> <li>Next <i><b>T</b></i> lines: A single integer<i><b>K</b></i> </ul> <p> <strong>Output format</strong> </p> <p> Print <i><b>T</b></i> lines representing the code to display the <i><b>K</b></i>th value. </p> <p> <strong>Constraints</strong> </p> <p> 1 <code> < </code> T <code> < </code> 10 </p> <p> 1 <code> < </code> K <code> < </code> 10 </p> <p> <strong>Explanation</strong> </p>",
        isSubmitted : false,
        type : ProblemType.PROGRAM,   
        languagesAllowed : [            
            { id: 1, editorCode : 'python', name: 'Python' },                      
            { id: 2, editorCode : 'java', name: 'Java (openjdk 1.7.0_95)' },
            { id: 3, editorCode : 'java', name: 'Java 14 (oracle 14)' }
        ],        
        placeHolderSolution : {
            1 : "class Simple{\r\n\r\n  public static void main(String args[]){\r\n \t System.out.println(\"Hello Java\"); \r\n  } \r\n\r\n }",
            2 : "class Simple1{\r\n\r\n  public static void main(String args[]){\r\n \t System.out.println(\"Hello Java\"); \r\n  } \r\n\r\n }"
        },
        testCases : [{
            id : 1,
            input : "10 5",
            expectedOutput : "5"
        }],     
        startDate : new Date("2021-10-01T00:00:00"),
        endDate : new Date("2021-10-16T00:00:00"),                        
    }    
]

let problemList : Response<Array<Problem>> = {
    data : problems
}

export { problemList }

