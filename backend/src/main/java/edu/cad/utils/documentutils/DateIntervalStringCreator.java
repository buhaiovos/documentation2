package edu.cad.utils.documentutils;

import java.time.LocalDate;

public class DateIntervalStringCreator {
    public static String getDatesString(LocalDate start, LocalDate finish) {
        return "з " + start + " по " + finish;
    }
}
