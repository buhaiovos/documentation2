package edu.cad.entities;

import edu.cad.entities.interfaces.IDatabaseEntity;
import edu.cad.entities.interfaces.SubjectProperty;
import edu.cad.entities.listeners.SubjectListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@EntityListeners(SubjectListener.class)
@Table(name = "academic_subject")
public class SubjectInfo extends YearTracked implements IDatabaseEntity<Integer>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;


    @Column(name = "semester")
    private int semester;


    @Column(name = "semesters_duration")
    private int semestersDuration;


    @Column(name = "lectures")
    private double lectures;


    @Column(name = "actual_lectures")
    private double actualLectures;


    @Column(name = "labs")
    private double labs;


    @Column(name = "actual_labs")
    private double actualLabs;


    @Column(name = "practices")
    private double practices;


    @Column(name = "actual_practices")
    private double actualPractices;


    @Column(name = "ECTS")
    private double ects;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_subject")
    private SubjectHeader subjectHeader;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "subjectInfo", orphanRemoval = true)
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

    public void setControls(Set<Control> newControls) {
        Set<Control> forRemoval = this.controls.stream()
                .filter(control -> !newControls.contains(control))
                .collect(toSet());
        forRemoval.forEach(c -> c.setSubjectInfo(null));
        this.controls.removeAll(forRemoval);
        newControls.stream()
                .filter(not(this.controls::contains))
                .forEach(c -> this.controls.add(c));
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

    public double getIndividualHours() {
        double individualLectures = actualLectures > 0 ? lectures - actualLectures : 0;
        double individualPractices = actualPractices > 0 ? practices - actualPractices : 0;
        double individualLabs = actualLabs > 0 ? labs - actualLabs : 0;

        return individualLectures + individualPractices + individualLabs;
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
