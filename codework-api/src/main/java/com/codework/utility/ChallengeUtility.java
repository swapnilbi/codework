package com.codework.utility;

public class ChallengeUtility {

    public static String getSubmissionStatusDescription(int status){
        switch(status){
            case 3 :
                return "Compiled Successfully";
            case 4 :
                return "Wrong answer";
            case 5 :
                return "Time Limit Exceeded";
            case 6 :
                return "Compilation Error";
            case 7 :
            case 8 :
            case 9 :
            case 10 :
            case 11 :
            case 12 :
                return "Runtime Error";
            default :
                return "Internal Error";
        }
    }

    public static boolean getCompilationStatus(int status){
        switch(status){
            case 3 :
            case 4 :
            case 5 :
                return true;
            default : return false;
        }
    }

}
