package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "dict_subjects")
public class SubjectDictionary implements IDatabaseEntity, Serializable, Comparable<SubjectDictionary> {
    
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_supersubject")
    private SubjectDictionary superSubject;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "superSubject")//subjectDictionary")
    private Set<SubjectDictionary> subSubjects = new HashSet<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_section_curriculum")
    private Section curriculumSection;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_section_workplan")
    private Section workplanSection;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type")
    private SubjectType type;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_department")
    private Department department;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
    private Set<Subject> academicSubjects = new HashSet<>();

    public SubjectDictionary() {
    }

    public SubjectDictionary(int id, String denotation, 
                    SubjectDictionary superSubject, Section curriculumSection, 
                    Section workplanSection, SubjectType type, 
                    Department department) {
        this.id = id;
        this.denotation = denotation;
        this.superSubject = superSubject;
        this.curriculumSection = curriculumSection;
        this.workplanSection = workplanSection;
        this.type = type;
        this.department = department;
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

    public SubjectDictionary getSuperSubject() {
        return superSubject;
    }

    public void setSuperSubject(SubjectDictionary superSubject) {
        this.superSubject = superSubject;
    }

    public Set<SubjectDictionary> getSubSubjects() {
        return subSubjects;
    }

    public void setSubSubjects(Set<SubjectDictionary> subSubjects) {
        this.subSubjects.clear();
        this.subSubjects.addAll(subSubjects);
    }

    public Section getCurriculumSection() {
        return curriculumSection;
    }

    public void setCurriculumSection(Section curriculumSection) {
        this.curriculumSection = curriculumSection;
    }

    public Section getWorkplanSection() {
        return workplanSection;
    }

    public void setWorkplanSection(Section workplanSection) {
        this.workplanSection = workplanSection;
    }

    public SubjectType getType() {
        return type;
    }

    public void setType(SubjectType type) {
        this.type = type;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Subject> getAcademicSubjects() {
        return academicSubjects;
    }

    public void setAcademicSubjects(Set<Subject> academicSubjects) {
        this.academicSubjects.clear();
        this.academicSubjects.addAll(academicSubjects);
    }
    
    public Subject findAppropriate(Curriculum curriculum){
        Qualification current = curriculum.getQualification();
        
        for(Subject element : academicSubjects){
            for(CurriculumSubject currSubject : element.getCurriculumSubjects()){
                if(currSubject.getCurriculum().getQualification().equals(current)){
                    return element;
                }
            } 
        }

        return null;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.id;
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
        final SubjectDictionary other = (SubjectDictionary) obj;
        if (this.id != other.getId()) {
            return false;
        }
        return true;
    } 
    
    @Override
    public int compareTo(SubjectDictionary other) {
        if(this.department == null && other.getDepartment() == null)
            return 0;
        
        if(this.department == null)
            return 1;
        
        if(other.department == null)
            return -1;
        
        if(this.department.equals(other.getDepartment()))
            return this.denotation.compareTo(other.getDenotation());
        
        return this.department.getId() - other.getDepartment().getId();
    }
}

