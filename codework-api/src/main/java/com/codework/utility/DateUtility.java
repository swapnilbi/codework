package com.codework.utility;

import org.apache.commons.codec.binary.Base64;

import java.util.Calendar;
import java.util.Date;

public class DateUtility {

    public static Date currentDate(){
        return Calendar.getInstance().getTime();
    }
}
