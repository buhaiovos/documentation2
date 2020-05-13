package edu.cad.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = {"id", "cipher"})
@Entity
@Table(name = "academic_group")
public class AcademicGroup extends YearTracked implements IDatabaseEntity<Integer>, Serializable, Comparable<AcademicGroup> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "cipher")
    private String cipher;

    @Column(name = "budgetary_students")
    private int budgetaryStudents;

    @Column(name = "contract_students")
    private int contractStudents;

    @Column(name = "start_year")
    private int startYear;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_specialization")
    @JsonIgnore
    private Specialization specialization;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_qualification")
    @JsonIgnore
    private Qualification qualification;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_education_form")
    @JsonIgnore
    private EducationForm educationForm;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_workplan")
    @JsonIgnore
    private WorkingPlan workingPlan;

    public AcademicGroup() {
    }

    @JsonIgnore
    public Department getDepartment() {
        return specialization.getDepartment();
    }

    public int getTotalStudents() {
        return budgetaryStudents + contractStudents;
    }

    public boolean isBudgetary() {
        return budgetaryStudents > 0;
    }

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }

    @Override
    public int compareTo(AcademicGroup other) {
        return other.startYear - this.startYear;
    }
}
