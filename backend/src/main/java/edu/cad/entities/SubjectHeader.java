package edu.cad.entities;

import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "dict_subjects")
public class SubjectHeader
        extends YearTracked
        implements IDatabaseEntity<Integer>, Serializable, Comparable<SubjectHeader> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;


    @Column(name = "denotation")
    private String denotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_supersubject")
    private SubjectHeader superSubject;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "superSubject")
    private Set<SubjectHeader> subSubjects = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_section_curriculum")
    private Section curriculumSection;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_section_workplan")
    private Section workingPlanSection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type")
    private SubjectType type;

    @ManyToOne(fetch = FetchType.EAGER)
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

    public void setSubjectInfo(Set<SubjectInfo> subjectInfo) {
        this.subjectInfo.clear();
        this.subjectInfo.addAll(subjectInfo);
    }

    public SubjectInfo findAppropriate(Curriculum curriculum) {
        Qualification current = curriculum.getQualification();

        for (SubjectInfo element : subjectInfo) {
            for (CurriculumSubject subject : element.getCurriculumSubjects()) {
                var qualification = subject.getCurriculum().getQualification();
                if (qualification != null && qualification.equals(current)) {
                    return element;
                }
            }
        }

        return null;
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

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}

