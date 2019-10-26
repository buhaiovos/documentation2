package edu.cad.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@Entity
@DiscriminatorValue("workplan")
public class WorkingPlan extends Curriculum implements Comparable<WorkingPlan> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_practice")
    private Practice practice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_state_certification")
    private StateCertification stateCertification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curriculum")
    private Curriculum curriculum;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.curriculum", cascade = CascadeType.MERGE)
    private Set<CurriculumSubject> workplanSubjects = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workingPlan")
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
    public Set<CurriculumSubject> getCurriculumSubjects() {
        return workplanSubjects;
    }

    @Override
    public void setCurriculumSubjects(Set<CurriculumSubject> workplanSubjects) {
        this.workplanSubjects.clear();
        this.workplanSubjects.addAll(workplanSubjects);
    }

    public void setDiplomaPreparations(Set<DiplomaPreparation> diplomaPreparations) {
        this.diplomaPreparations.clear();
        this.diplomaPreparations.addAll(diplomaPreparations);
    }

    public void setGroups(Set<AcademicGroup> groups) {
        this.groups.clear();
        this.groups.addAll(groups);
    }

    @Override
    public Qualification getQualification() {
        return groups.iterator().next().getQualification();
    }

    public EducationForm getEducationForm(){
        return groups.iterator().next().getEducationForm();
    }

    public int getStartYear(){
        return groups.iterator().next().getStartYear();
    }

    public int countBudgetaryStudents(){
        int total = 0;

        for(AcademicGroup group : groups){
            total += group.getBudgetaryStudents();
        }

        return total;
    }

    public int countContractStudents(){
        int total = 0;

        for(AcademicGroup group : groups){
            total += group.getContractStudents();
        }

        return total;
    }

    public int countTotalStudents(){
        int total = 0;

        for(AcademicGroup group : groups){
            total += group.getTotalStudents();
        }

        return total;
    }

    @Override
    public int compareTo(WorkingPlan o) {
        if(!getEducationForm().equals(o.getEducationForm()))
            return getEducationForm().getId() - o.getEducationForm().getId();

        if(!getQualification().equals(o.getQualification()))
            return getQualification().getId() - o.getQualification().getId();

        return o.getStartYear() - getStartYear();
    }
}
