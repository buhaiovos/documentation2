package edu.cad.utils.documentutils;

import java.text.DateFormat;
import java.util.Date;

public class DateIntervalStringCreator {
    
    public static String getDatesString(Date st, Date fin) {
        String start = getProperlyFormattedDate(st);
        String finish = getProperlyFormattedDate(fin);
        
        return "з " + start + " по " + finish;
    }

    private static String getProperlyFormattedDate(Date date) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        return df.format(date).substring(0, 5);
    }
}
