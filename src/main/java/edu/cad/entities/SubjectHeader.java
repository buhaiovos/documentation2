package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "dict_subjects")
public class SubjectHeader implements IDatabaseEntity, Serializable, Comparable<SubjectHeader> {

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
    private SubjectHeader superSubject;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "superSubject")//subjectDictionary")
    private Set<SubjectHeader> subSubjects = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_section_curriculum")
    private Section curriculumSection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_section_workplan")
    private Section workingPlanSection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type")
    private SubjectType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_department")
    private Department department;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subjectHeader")
    private Set<SubjectInfo> subjectInfo = new HashSet<>();

    public SubjectHeader() {
    }

    public SubjectHeader(int id, String denotation,
                         SubjectHeader superSubject, Section curriculumSection,
                         Section workingPlanSection, SubjectType type,
                         Department department) {
        this.id = id;
        this.denotation = denotation;
        this.superSubject = superSubject;
        this.curriculumSection = curriculumSection;
        this.workingPlanSection = workingPlanSection;
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

    public void setSubjectInfo(Set<SubjectInfo> subjectInfo) {
        this.subjectInfo.clear();
        this.subjectInfo.addAll(subjectInfo);
    }

    public SubjectInfo findAppropriate(Curriculum curriculum) {
        Qualification current = curriculum.getQualification();

        for (SubjectInfo element : subjectInfo) {
            for (CurriculumSubject currSubject : element.getCurriculumSubjects()) {
                if (currSubject.getCurriculum().getQualification().equals(current)) {
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
        final SubjectHeader other = (SubjectHeader) obj;
        return this.id == other.id;
    }

    @Override
    public int compareTo(SubjectHeader other) {
        if (this.department == null)
            return 1;

        if (other.department == null)
            return -1;

        if (this.department.equals(other.department))
            return this.denotation.compareTo(other.denotation);

        return this.department.getId() - other.department.getId();
    }
}

