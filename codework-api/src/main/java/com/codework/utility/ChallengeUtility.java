package com.codework.utility;

import org.apache.commons.codec.binary.Base64;

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

    public static boolean isCompilationError(int status){
        return status == 6;
    }
    
    public static boolean isRuntimeError(int status){
        switch(status){
	        case 7 :
	        case 8 :
	        case 9 :
	        case 10 :
	        case 11 :
	        case 12 :
                return true;
            default : return false;
        }
    }
    
    public static String encodeToBase64(String input) {
    	if(input!=null) {
			return new String(Base64.encodeBase64(input.trim().getBytes()));
    	}
    	return null;
    }
    
    public static String decodeFromBase64(String encodedString) {
    	if(encodedString!=null) {
			return new String(Base64.decodeBase64(encodedString.trim().getBytes()));
    	}
    	return null;
    }

}
