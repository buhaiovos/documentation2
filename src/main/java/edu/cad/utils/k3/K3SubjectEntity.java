package edu.cad.utils.k3;

import edu.cad.entities.Subject;
import java.util.HashMap;
import java.util.Map;

public class K3SubjectEntity {
    
    private int number;
    private final Subject subject;
    private final Map<TypeOfGroupWork, Integer> subgroups;
    
    public K3SubjectEntity(Subject subject){
        this.subject = subject;
        subgroups = new HashMap<>();
        
        for(TypeOfGroupWork type : TypeOfGroupWork.values()){
            subgroups.put(type, 0);
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    public Subject getSubject(){
        return subject;
    }
    
    public int getSubgroup(TypeOfGroupWork type){
        return subgroups.get(type);
    }
    
    public void addSubgroups(TypeOfGroupWork type, int value){
        subgroups.put(type, subgroups.get(type) + value);
    }
    
    public void resetSubgroups(TypeOfGroupWork type){
        subgroups.put(type, 0);
    }
}
