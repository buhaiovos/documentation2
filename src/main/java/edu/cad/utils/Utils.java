package edu.cad.utils;

public class Utils {
    
    public static boolean isParseable(String value){
        try{
            Double.parseDouble(value);
        } catch(NumberFormatException ex) {
            return false;
        }
        
        return true;
    }
}
