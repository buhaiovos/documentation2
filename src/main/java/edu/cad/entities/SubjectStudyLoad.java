package edu.cad.entities;

import edu.cad.entities.interfaces.IDatabaseEntity;
import edu.cad.utils.k3.SourceOfFinancing;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@ToString
@Entity
@Table(name = "subject_study_load")
public class SubjectStudyLoad extends YearTracked implements IDatabaseEntity<Integer>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_education_form")
    private EducationForm educationForm;

    @Column(name = "financing")
    @Enumerated(EnumType.STRING)
    private SourceOfFinancing sourceOfFinancing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_academic_subject")
    private SubjectInfo subjectInfo;

    @Column(name = "lectures")
    private double lectures;

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

    @Column(name = "course_works")
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
        final SubjectStudyLoad other = (SubjectStudyLoad) obj;
        return this.id == other.getId();
    }

}
