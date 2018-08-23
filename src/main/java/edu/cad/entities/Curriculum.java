package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.GenericGenerator;

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
    private Set<Workplan> workplans = new HashSet<>();

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

    public Set<Workplan> getWorkplans() {
        return workplans;
    }

    public void setWorkplans(Set<Workplan> workplans) {
        this.workplans.clear();
        this.workplans.addAll(workplans);
    }
    
    public Qualification getQualification() {
        return workplans.iterator().next().getQualification();
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
        if (this.id != other.getId()) {
            return false;
        }
        return true;
    }
    
    public boolean contains(Subject subject){
        for(Workplan plan : getWorkplans()){
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
