package edu.cad.entities;

import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "section")
public class Section extends YearTracked implements IDatabaseEntity<Integer>, Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;


    @Column(name = "denotation")
    private String denotation;


    @Column(name = "is_optional")
    private boolean optional;

    @ManyToOne(fetch = FetchType.EAGER)
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

    public void setCurriculumSubjects(Set<SubjectHeader> curriculumSubjects) {
        this.curriculumSubjects.clear();
        this.curriculumSubjects.addAll(curriculumSubjects);
    }

    public void setWorkingPlanSubjects(Set<SubjectHeader> workingPlanSubjects) {
        this.workingPlanSubjects.clear();
        this.workingPlanSubjects.addAll(workingPlanSubjects);
    }

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
