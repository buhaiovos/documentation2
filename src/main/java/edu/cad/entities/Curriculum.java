package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "curriculum")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "curriculum")
@DiscriminatorOptions(force = true)
public class Curriculum extends YearTracked implements IDatabaseEntity<Integer>, Serializable {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Integer id;

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
