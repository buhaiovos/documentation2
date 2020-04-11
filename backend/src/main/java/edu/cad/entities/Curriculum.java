package edu.cad.entities;

import com.google.gson.annotations.Expose;
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
@EqualsAndHashCode(of = "id", callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "curriculum")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "curriculum")
public class Curriculum extends YearTracked implements IDatabaseEntity<Integer>, Serializable {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Integer id;

    @Expose
    @Column(name = "denotation")
    protected String denotation;

    @Column(name = "type", updatable = false, insertable = false)
    protected String type;

    @OneToMany(
            fetch = FetchType.EAGER, mappedBy = "pk.curriculum",
            cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private Set<CurriculumSubject> curriculumSubjects = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "curriculum")
    private Set<WorkingPlan> workingPlans = new HashSet<>();

    public Curriculum() {
    }

    public Curriculum(int id) {
        this.id = id;
    }

    public Curriculum setCurriculumSubjects(Set<CurriculumSubject> curriculumSubjects) {
        this.curriculumSubjects.clear();
        this.curriculumSubjects.addAll(curriculumSubjects);
        return this;
    }

    public Curriculum setWorkingPlans(Set<WorkingPlan> workingPlans) {
        this.workingPlans.clear();
        this.workingPlans.addAll(workingPlans);
        return this;
    }

    public Qualification getQualification() {
        var iterator = workingPlans.iterator();
        if (iterator.hasNext()) {
            return iterator.next().getQualification();
        }
        return null;
    }

    public boolean contains(SubjectInfo subjectInfo) {
        for (WorkingPlan plan : getWorkingPlans()) {
            for (CurriculumSubject currSubject : plan.getCurriculumSubjects()) {
                if (currSubject.getSubjectInfo().equals(subjectInfo)) {
                    return true;
                }
            }
        }

        return false;
    }

    public int countControlsByType(int semester, ControlDictionary type) {
        int total = 0;

        for (CurriculumSubject curriculumSubject : getCurriculumSubjects()) {
            for (SubjectInfo subjectInfo : curriculumSubject.getSubjectInfo().getSubSubjects(this)) {
                for (Control control : subjectInfo.getControlsByType(type)) {
                    if (control.getSemester() == semester) {
                        total++;
                    }
                }
            }
        }

        return total;
    }

    public int countControlsByType(ControlDictionary type) {
        int total = 0;

        for (CurriculumSubject curriculumSubject : getCurriculumSubjects()) {
            for (SubjectInfo subjectInfo : curriculumSubject.getSubjectInfo().getSubSubjects(this)) {
                total += subjectInfo.getControlsByType(type).size();
            }
        }

        return total;
    }

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
