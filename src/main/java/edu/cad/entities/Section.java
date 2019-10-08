package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "section")
public class Section extends YearTracked implements IDatabaseEntity<Integer>, Serializable {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Expose
    @Column(name = "denotation")
    private String denotation;

    @Expose
    @Column(name = "is_optional")
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
