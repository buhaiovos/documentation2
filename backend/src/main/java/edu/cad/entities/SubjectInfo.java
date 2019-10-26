package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.daos.HibernateDao;
import edu.cad.entities.interfaces.IDatabaseEntity;
import edu.cad.entities.interfaces.SubjectProperty;
import edu.cad.entities.listeners.SubjectListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@Entity
@EntityListeners(SubjectListener.class)
@Table(name = "academic_subject")
public class SubjectInfo extends YearTracked implements IDatabaseEntity<Integer>, Serializable {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Expose
    @Column(name = "semester")
    private int semester;

    @Expose
    @Column(name = "semesters_duration")
    private int semestersDuration;

    @Expose
    @Column(name = "lectures")
    private double lectures;

    @Expose
    @Column(name = "actual_lectures")
    private double actualLectures;

    @Expose
    @Column(name = "labs")
    private double labs;

    @Expose
    @Column(name = "actual_labs")
    private double actualLabs;

    @Expose
    @Column(name = "practices")
    private double practices;

    @Expose
    @Column(name = "actual_practices")
    private double actualPractices;

    @Expose
    @Column(name = "ECTS")
    private double ects;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subject")
    private SubjectHeader subjectHeader;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subjectInfo")
    private Set<Control> controls = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.subjectInfo", cascade = CascadeType.MERGE)
    private Set<CurriculumSubject> curriculumSubjects = new HashSet<>();

    @Transient
    private Map<Curriculum, Set<SubjectInfo>> subSubjects = new HashMap<>();

    @Transient
    private Set<AcademicGroup> groups = new HashSet<>();

    public SubjectInfo() {
    }

    public SubjectInfo(int id, int semester, int semestersDuration, int lectures,
                       int labs, int practices, float ects, SubjectHeader subjectHeader) {
        this.id = id;
        this.semester = semester;
        this.semestersDuration = semestersDuration;
        this.lectures = lectures;
        this.labs = labs;
        this.practices = practices;
        this.ects = ects;
        this.subjectHeader = subjectHeader;
    }

    public Set<Control> getControls() {
        return controls;
    }

    public void setControls(Set<Control> controls) {
        this.controls.clear();
        this.controls.addAll(controls);
    }

    public Set<CurriculumSubject> getCurriculumSubjects() {
        return curriculumSubjects;
    }

    public void setCurriculumSubjects(Set<CurriculumSubject> curriculumSubjects) {
        this.curriculumSubjects.clear();
        this.curriculumSubjects.addAll(curriculumSubjects);
    }

    public double getTotalHours() {
        return lectures + labs + practices;
    }

    public double getEctsHours() {
        return (ects * 30);
    }

    public double getEctsHoursWithoutExam() {
        if (hasCourseWork())
            return getEctsHours() - 30;

        return getEctsHours();
    }

    public int getSemesterHours(int currSemester, Curriculum curriculum,
                                SubjectProperty property) {
        Set<SubjectInfo> subjectDetails = getSubSubjects(curriculum);
        int total = 0;

        for (SubjectInfo subjectDetail : subjectDetails) {
            total += subjectDetail.getSemesterHours(currSemester, property);
        }

        return total;
    }

    private double getSemesterHours(int currSemester, SubjectProperty property) {
        for (int i = semester; i < semester + semestersDuration; i++) {
            if (i == currSemester)
                return property.getValue(this) / semestersDuration;
        }

        return 0;
    }

    public Set<Control> getControlsByType(ControlDictionary type) {
        Set<Control> result = new HashSet<>();

        for (Control control : controls) {
            if (control.getType().equals(type)) {
                result.add(control);
            }
        }

        return result;
    }

    public boolean hasControlOfType(ControlDictionary type) {
        for (Control control : controls) {
            if (control.getType().equals(type)) {
                return true;
            }
        }

        return false;
    }

    public Set<SubjectInfo> getSubSubjects(Curriculum curriculum) {
        return subSubjects.get(curriculum);
    }

    public void setSubSubjects(Curriculum curriculum, Set<SubjectInfo> subjectDetails) {
        subSubjects.put(curriculum, subjectDetails);
    }

    public Set<AcademicGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<AcademicGroup> groups) {
        this.groups.clear();
        groups.addAll(groups);
    }

    public boolean hasCourseWork() {
        ControlDictionary courseWork = new HibernateDao<>(ControlDictionary.class).get(5);

        for (SubjectHeader subjectHeader : getSubjectHeader().getSubSubjects()) {
            for (SubjectInfo element : subjectHeader.getSubjectInfo()) {
                if (element.hasControlOfType(courseWork)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isCourseWork() {
        ControlDictionary courseWork = new HibernateDao<>(ControlDictionary.class).get(5);

        return hasControlOfType(courseWork);
    }

    public double getIndividualHours() {
        double individualLectures = actualLectures > 0 ? lectures - actualLectures : 0;
        double individualPractices = actualPractices > 0 ? practices - actualPractices : 0;
        double individualLabs = actualLabs > 0 ? labs - actualLabs : 0;

        return individualLectures + individualPractices + individualLabs;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + this.id;
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
        final SubjectInfo other = (SubjectInfo) obj;
        return this.id == other.getId();
    }

    @Override
    public String toString() {
        StringBuilder value = new StringBuilder();
        value.append(subjectHeader.getDenotation())
                .append("[ ").append(semester).append("-й семестр ]")
                .append(" [")
                .append(" ECTS : ").append(ects)
                .append(" лекції : ").append(lectures)
                .append(" лабораторні : ").append(labs)
                .append(" практичні : ").append(practices)
                .append(" ]");
        return value.toString();
    }

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
