package edu.cad.utils.k3;

import edu.cad.entities.SubjectInfo;

import java.util.HashMap;
import java.util.Map;

public class K3SubjectEntity {
    
    private int number;
    private final SubjectInfo subjectInfo;
    private final Map<TypeOfGroupWork, Integer> subgroups;

    public K3SubjectEntity(SubjectInfo subjectInfo) {
        this.subjectInfo = subjectInfo;
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

    public SubjectInfo getSubjectInfo() {
        return subjectInfo;
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
