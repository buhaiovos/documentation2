package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "section")
public class Section implements IDatabaseEntity, Serializable{
    
    @Expose
    @Id
    @GenericGenerator(
        name = "assigned-identity", 
        strategy = "edu.cad.utils.hibernateutils.AssignedIdentityGenerator"
    )
    @GeneratedValue(generator = "assigned-identity")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @Expose
    @Column(name = "denotation")
    private String denotation;
    
    @Expose
    @Column(name = "is_optional", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean optional;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cycle")
    private Cycle cycle;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "curriculumSection")
    private Set<SubjectHeader> curriculumSubjects = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workingPlanSection")
    private Set<SubjectHeader> workingPlanSubjects = new HashSet<>();

    public Section() {
    }

    public Section(int id, String denotation, boolean isOptional, Cycle cycle) {
        this.id = id;
        this.denotation = denotation;
        this.optional = isOptional;
        this.cycle = cycle;
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

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public Set<SubjectHeader> getCurriculumSubjects() {
        return curriculumSubjects;
    }

    public void setCurriculumSubjects(Set<SubjectHeader> curriculumSubjects) {
        this.curriculumSubjects.clear();
        this.curriculumSubjects.addAll(curriculumSubjects);
    }

    public Set<SubjectHeader> getWorkingPlanSubjects() {
        return workingPlanSubjects;
    }

    public void setWorkingPlanSubjects(Set<SubjectHeader> workingPlanSubjects) {
        this.workingPlanSubjects.clear();
        this.workingPlanSubjects.addAll(workingPlanSubjects);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
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
        final Section other = (Section) obj;
        return this.id == other.getId();
    }
}
