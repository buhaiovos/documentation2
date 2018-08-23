package edu.cad.utils.databaseutils;

import edu.cad.utils.Utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;

public class DatabaseYears {
    private static String FILE = "WEB-INF/classes/DatabaseYears.txt";
    
    public static void setYearsFilePath(String path) {
        FILE = path;
    }
    
    public static Set<Integer> getAllYears(){
        Set<Integer> years = new TreeSet<>();
        
        try (BufferedReader in = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = in.readLine()) != null) {
                if(Utils.isParseable(line)){
                    years.add(Integer.parseInt(line));
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } 

        return years;
    }
    
    public static void addYear(int year){
        try(PrintWriter out = new PrintWriter(new BufferedWriter(
                new FileWriter(FILE, true)))) {
            out.println();
            out.print(year);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public static void deleteYear(int year){
        Set<Integer> years = getAllYears();
        clearFile();
        
        for(int element : years){
            if(element != year){
                addYear(element);
            }
        }  
    }
    
    private static void clearFile(){  
        try (PrintWriter out = new PrintWriter(FILE)){
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
}
