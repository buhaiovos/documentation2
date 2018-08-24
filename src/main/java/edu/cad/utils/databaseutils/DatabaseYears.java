package edu.cad.utils.databaseutils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DatabaseYears {
    private static String FILE = "DatabaseYears.txt";
    
    public static void setYearsFilePath(String path) {
        FILE = path;
    }
    
    public static Set<Integer> getAllYears(){
        final Path pathToYearsTrackingFile = Paths.get(FILE);
        try {
            return Files.lines(pathToYearsTrackingFile)
                    .mapToInt(Integer::valueOf)
                    .boxed()
                    .collect(Collectors.toCollection(TreeSet::new));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read existing database years from tracking file", e);
        }
    }

    public static void addYearToYearsTrackingFile(int year) {
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
                addYearToYearsTrackingFile(element);
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
