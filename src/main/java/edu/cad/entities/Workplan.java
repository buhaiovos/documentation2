package edu.cad.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@DiscriminatorValue("workplan")
public class Workplan extends Curriculum implements Comparable<Workplan>{
    
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
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workplan")
    private Set<DiplomaPreparation> diplomaPreparations = new HashSet<>();
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workplan")
    private Set<AcademicGroup> groups = new HashSet<>();

    public Workplan() {
    }

    public Workplan(int id, Practice practice, 
            StateCertification stateCertification, Curriculum curriculum) {
        super(id);
        this.practice = practice;
        this.stateCertification = stateCertification;
        this.curriculum = curriculum;
    }

    public Practice getPractice() {
        return practice;
    }

    public void setPractice(Practice practice) {
        this.practice = practice;
    }

    public StateCertification getStateCertification() {
        return stateCertification;
    }

    public void setStateCertification(StateCertification stateCertification) {
        this.stateCertification = stateCertification;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
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

    public Set<DiplomaPreparation> getDiplomaPreparations() {
        return diplomaPreparations;
    }

    public void setDiplomaPreparations(Set<DiplomaPreparation> diplomaPreparations) {
        this.diplomaPreparations.clear();
        this.diplomaPreparations.addAll(diplomaPreparations);
    }

    public Set<AcademicGroup> getGroups() {
        return groups;
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
    public int compareTo(Workplan o) {
        if(!getEducationForm().equals(o.getEducationForm()))
            return getEducationForm().getId() - o.getEducationForm().getId();
        
        if(!getQualification().equals(o.getQualification()))
            return getQualification().getId() - o.getQualification().getId();
        
        return o.getStartYear() - getStartYear();
    }
}

