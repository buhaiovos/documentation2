package edu.cad.entities;

import edu.cad.entities.interfaces.IDatabaseEntity;
import edu.cad.utils.k3.SourceOfFinancing;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="study_load_results")
public class StudyLoadResults implements IDatabaseEntity, Serializable{
    
    @Id
    @GenericGenerator(
        name = "assigned-identity", 
        strategy = "edu.cad.utils.hibernateutils.AssignedIdentityGenerator"
    )
    @GeneratedValue(generator = "assigned-identity")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_education_form")
    private EducationForm educationForm;
    
    @Column(name = "financing")
    @Enumerated(EnumType.ORDINAL)
    private SourceOfFinancing sourceOfFinancing;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_academic_subject")
    private Subject subject;
    
    @Column(name = "lections")
    private double lections;
    
    @Column(name = "practices")
    private double practices;
    
    @Column(name = "labs")
    private double labs;
    
    @Column(name = "individuals")
    private double individuals;
        
    @Column(name = "exams")
    private double exams;
    
    @Column(name = "credits")
    private double credits;
    
    @Column(name = "contr_works")
    private double controlWorks;
    
    @Column(name = "course_projs")
    private double courseProjects;
    
    @Column(name = "cource_works")
    private double courseWorks;
    
    @Column(name = "rgr")
    private double RGRs;
    
    @Column(name = "dkr")
    private double DKRs;
    
    @Column(name = "referats")
    private double referats;
    
    @Column(name = "consult")
    private double consultations;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public EducationForm getEducationForm() {
        return educationForm;
    }

    public void setEducationForm(EducationForm educationForm) {
        this.educationForm = educationForm;
    }

    public SourceOfFinancing getSourceOfFinancing() {
        return sourceOfFinancing;
    }

    public void setSourceOfFinancing(SourceOfFinancing sourceOfFinancing) {
        this.sourceOfFinancing = sourceOfFinancing;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public double getLections() {
        return lections;
    }

    public void setLections(double lections) {
        this.lections = lections;
    }

    public double getPractices() {
        return practices;
    }

    public void setPractices(double practices) {
        this.practices = practices;
    }

    public double getLabs() {
        return labs;
    }

    public void setLabs(double labs) {
        this.labs = labs;
    }

    public double getIndividuals() {
        return individuals;
    }

    public void setIndividuals(double individuals) {
        this.individuals = individuals;
    }

    public double getExams() {
        return exams;
    }

    public void setExams(double exams) {
        this.exams = exams;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public double getControlWorks() {
        return controlWorks;
    }

    public void setControlWorks(double controlWorks) {
        this.controlWorks = controlWorks;
    }

    public double getCourseProjects() {
        return courseProjects;
    }

    public void setCourseProjects(double courseProjects) {
        this.courseProjects = courseProjects;
    }

    public double getCourseWorks() {
        return courseWorks;
    }

    public void setCourseWorks(double courseWorks) {
        this.courseWorks = courseWorks;
    }

    public double getRGRs() {
        return RGRs;
    }

    public void setRGRs(double RGRs) {
        this.RGRs = RGRs;
    }

    public double getDKRs() {
        return DKRs;
    }

    public void setDKRs(double DKRs) {
        this.DKRs = DKRs;
    }

    public double getReferats() {
        return referats;
    }

    public void setReferats(double referats) {
        this.referats = referats;
    }

    public double getConsultations() {
        return consultations;
    }

    public void setConsultations(double consultations) {
        this.consultations = consultations;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        /*
        if (getClass() != obj.getClass()) {
            return false;
        }
        */
        final StudyLoadResults other = (StudyLoadResults) obj;
        if (this.id != other.getId()) {
            return false;
        }
        return true;
    }
   
}
