package com.javeria.notes.util;

import java.text.DateFormat;
import java.util.Date;

public class Utility {

    public static String getCurrentTimestamp(){
        String timestamp = "";
        long time = System.currentTimeMillis();
        DateFormat.getInstance().format(time);
        timestamp =    DateFormat.getDateInstance().format(new Date(0));
//        timestamp = DateFormat.getDateTimeInstance().format(new Date(0));
        return timestamp;
    }
}
