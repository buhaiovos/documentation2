package edu.cad.utils.k3;

public enum TypeOfGroupWork {
    Academic, Practice, Lab, OtherSource;
    
    public int getMinStudents(){
        switch(this){
            case Practice   :   return 20;
            case Lab        :   return 12;  
        }
        
        return Integer.MIN_VALUE;
    }
    
    public int getMaxStudents(){
        switch(this){
            case Practice   :   return 35;
            case Lab        :   return 23;
        }
        
        return Integer.MAX_VALUE;
    }
}
