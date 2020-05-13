package edu.cad.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@Accessors(chain = true)
@Entity
@DiscriminatorValue("workplan")
public class WorkingPlan extends Curriculum implements Comparable<WorkingPlan> {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_practice")
    private Practice practice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_state_certification")
    private StateCertification stateCertification;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_curriculum")
    private Curriculum curriculum;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.curriculum", cascade = CascadeType.MERGE)
    private Set<CurriculumSubject> workplanSubjects = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "workingPlan")
    private Set<DiplomaPreparation> diplomaPreparations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workingPlan")
    private Set<AcademicGroup> groups = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_scientific_research_subject")
    private SubjectInfo scientificResearchSubject;

    public WorkingPlan() {
    }

    public WorkingPlan(int id, Practice practice,
                       StateCertification stateCertification, Curriculum curriculum) {
        super(id);
        this.practice = practice;
        this.stateCertification = stateCertification;
        this.curriculum = curriculum;
    }

    @Override
    public WorkingPlan setCurriculumSubjects(Set<CurriculumSubject> workplanSubjects) {
        this.workplanSubjects.clear();
        this.workplanSubjects.addAll(workplanSubjects);
        return this;
    }

    public WorkingPlan setDiplomaPreparations(Set<DiplomaPreparation> diplomaPreparations) {
        this.diplomaPreparations.clear();
        this.diplomaPreparations.addAll(diplomaPreparations);
        return this;
    }

    public WorkingPlan setGroups(Set<AcademicGroup> groups) {
        this.groups.clear();
        this.groups.addAll(groups);
        return this;
    }

    @Override
    public Qualification getQualification() {
        var iterator = groups.iterator();
        if (iterator.hasNext()) {
            return iterator.next().getQualification();
        }
        return null;
    }

    public EducationForm getEducationForm() {
        return groups.iterator().next().getEducationForm();
    }

    public int getStartYear() {
        return groups.iterator().next().getStartYear();
    }

    public int countBudgetaryStudents() {
        int total = 0;

        for (AcademicGroup group : groups) {
            total += group.getBudgetaryStudents();
        }

        return total;
    }

    public int countContractStudents() {
        int total = 0;

        for (AcademicGroup group : groups) {
            total += group.getContractStudents();
        }

        return total;
    }

    public int countTotalStudents() {
        int total = 0;

        for (AcademicGroup group : groups) {
            total += group.getTotalStudents();
        }

        return total;
    }

    @Override
    public int compareTo(WorkingPlan o) {
        if (!getEducationForm().equals(o.getEducationForm()))
            return getEducationForm().getId() - o.getEducationForm().getId();

        if (!getQualification().equals(o.getQualification()))
            return getQualification().getId() - o.getQualification().getId();

        return o.getStartYear() - getStartYear();
    }
}

