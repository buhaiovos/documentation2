package edu.cad.utils.k3;

import edu.cad.entities.AcademicGroup;

public enum SourceOfFinancing {
    Budgetary, 
    Contract;
    
    public boolean sourceEquals(AcademicGroup group){
        switch(this){
            case Budgetary  :   if(group.isBudgetary())     
                                    return true;
                                break;
            case Contract   :   if(!group.isBudgetary())    
                                    return true;
                                break;
        }
        
        return false;
    }
    
    public int getStudents(AcademicGroup group){
        switch(this){
            case Budgetary  :   return group.getBudgetaryStudents();
            case Contract   :   return group.getContractStudents();
        }
        
        return group.getTotalStudents();
    }
}
