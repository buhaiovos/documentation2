package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "curriculum")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "curriculum")
@DiscriminatorOptions(force=true)
public class Curriculum implements IDatabaseEntity, Serializable{
    
    @Expose
    @Id
    @GenericGenerator(
        name = "assigned-identity", 
        strategy = "edu.cad.utils.hibernateutils.AssignedIdentityGenerator"
    )
    @GeneratedValue(generator = "assigned-identity")
    @Column(name = "id", unique = true, nullable = false)
    protected int id;
    
    @Expose
    @Column(name = "denotation")
    protected String denotation;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.curriculum", cascade = CascadeType.MERGE)
    private Set<CurriculumSubject> curriculumSubjects = new HashSet<>();
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "curriculum")
    private Set<WorkingPlan> workingPlans = new HashSet<>();

    public Curriculum() {
    }

    public Curriculum(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getDenotation() {
        return denotation;
    }

    public void setDenotation(String denotation) {
        this.denotation = denotation;
    }

    public Set<CurriculumSubject> getCurriculumSubjects() {
        return curriculumSubjects;
    }

    public void setCurriculumSubjects(Set<CurriculumSubject> curriculumSubjects) {
        this.curriculumSubjects.clear();
        this.curriculumSubjects.addAll(curriculumSubjects);
    }

    public Set<WorkingPlan> getWorkingPlans() {
        return workingPlans;
    }

    public void setWorkingPlans(Set<WorkingPlan> workingPlans) {
        this.workingPlans.clear();
        this.workingPlans.addAll(workingPlans);
    }
    
    public Qualification getQualification() {
        return workingPlans.iterator().next().getQualification();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        /*if (getClass() != obj.getClass()) {
            return false;
        }*/
        final Curriculum other = (Curriculum) obj;
        return this.id == other.getId();
    }
    
    public boolean contains(Subject subject){
        for (WorkingPlan plan : getWorkingPlans()) {
            for(CurriculumSubject currSubject : plan.getCurriculumSubjects()){
                if(currSubject.getSubject().equals(subject)){
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public int countControlsByType(int semester, ControlDictionary type){
        int total = 0;
        
        for(CurriculumSubject curriculumSubject : getCurriculumSubjects()){
            for(Subject subject : curriculumSubject.getSubject().getSubSubjects(this)){
                for(Control control : subject.getControlsByType(type)){
                    if(control.getSemester() == semester){
                        total++;
                    }
                }
            } 
        }
        
        return total;
    }
    
    public int countControlsByType(ControlDictionary type){
        int total = 0;
        
        for(CurriculumSubject curriculumSubject : getCurriculumSubjects()){
            for(Subject subject : curriculumSubject.getSubject().getSubSubjects(this)){
                total += subject.getControlsByType(type).size();
            } 
        }
        
        return total;
    }
}
