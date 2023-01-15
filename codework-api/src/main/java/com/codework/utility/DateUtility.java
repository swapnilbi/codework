package com.codework.utility;

import java.util.Calendar;
import java.util.Date;

public class DateUtility {

    public static Date currentDate(){
        return Calendar.getInstance().getTime();
    }
}
