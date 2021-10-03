import { Problem, ProblemType } from "src/app/model/problem.model";

let problemList : Array<Problem> = [
    {
        id : 1,
        name : 'Problem 1',        
        problemStatement : "<p> You are given a set of binary elements. You have to eliminate the binary numbers that contain as a substring. The resultant sequence will be 1, 10, 100, 101, 1000, and so on. You are required to generate the code to determine the value of the new sequence. </p> <p> You are required to generate the code to determine the value of the new sequence. </p> <p> <strong>Input format</strong> </p> <ul> <li>First line: <i><b>T</b></i> denoting the number of test cases</li> <li>Next <i><b>T</b></i> lines: A single integer<i><b>K</b></i> </ul> <p> <strong>Output format</strong> </p> <p> Print <i><b>T</b></i> lines representing the code to display the <i><b>K</b></i>th value. </p> <p> <strong>Constraints</strong> </p> <p> 1 <code> < </code> T <code> < </code> 10 </p> <p> 1 <code> < </code> K <code> < </code> 10 </p> <p> <strong>Explanation</strong> </p>",
        isSubmitted : false,
        type : ProblemType.PROGRAM,           
        languagesAllowed : [
            { id: 'C', description: 'C (gcc 5.4.0)' },
            { id: 'CPP', description: 'C++ (g++ 5.4.0)' },
            { id: 'JAVA', description: 'Java (openjdk 1.7.0_95)' },
            { id: 'JAVA8', description: 'Java 14 (oracle 14)' }
        ],
        noOfTestCases : 5,
        placeHolderSolution : "class Simple{\r\n\r\n  public static void main(String args[]){\r\n \t System.out.println(\"Hello Java\"); \r\n  } \r\n\r\n }",        
        sampleTestCase : {
            input : "5 5",
            expectedOutput : "10"
        },     
        startDate : new Date("2021-10-01T00:00:00"),
        endDate : new Date("2021-10-16T00:00:00"),                        
    },
    {
        id : 2,
        name : 'Problem 2',        
        problemStatement : "<p> You are given a set of binary elements. You have to eliminate the binary numbers that contain as a substring. The resultant sequence will be 1, 10, 100, 101, 1000, and so on. You are required to generate the code to determine the value of the new sequence. </p> <p> You are required to generate the code to determine the value of the new sequence. </p> <p> <strong>Input format</strong> </p> <ul> <li>First line: <i><b>T</b></i> denoting the number of test cases</li> <li>Next <i><b>T</b></i> lines: A single integer<i><b>K</b></i> </ul> <p> <strong>Output format</strong> </p> <p> Print <i><b>T</b></i> lines representing the code to display the <i><b>K</b></i>th value. </p> <p> <strong>Constraints</strong> </p> <p> 1 <code> < </code> T <code> < </code> 10 </p> <p> 1 <code> < </code> K <code> < </code> 10 </p> <p> <strong>Explanation</strong> </p>",
        isSubmitted : false,
        type : ProblemType.PROGRAM,   
        languagesAllowed : [            
            { id: 'JAVA', description: 'Java (openjdk 1.7.0_95)' },
            { id: 'JAVA8', description: 'Java 14 (oracle 14)' }
        ],
        noOfTestCases : 5,
        placeHolderSolution : "class SimpleSolution{\r\n\r\n  public static void main(String args[]){\r\n \t System.out.println(\"Hello Java\"); \r\n  } \r\n\r\n }",
        sampleTestCase : {
            input : "10 5",
            expectedOutput : "5"
        },     
        startDate : new Date("2021-10-01T00:00:00"),
        endDate : new Date("2021-10-16T00:00:00"),                        
    }    
]

export { problemList }

