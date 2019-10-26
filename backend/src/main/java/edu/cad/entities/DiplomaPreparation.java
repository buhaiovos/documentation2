package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "diploma_preparation")
public class DiplomaPreparation extends YearTracked implements IDatabaseEntity<Integer>, Serializable {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Expose
    @Column(name = "norm")
    private float norm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_of_work")
    private WorkType workType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_department")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curriculum")
    private WorkingPlan workingPlan;

    public DiplomaPreparation() {
    }

    public DiplomaPreparation(int id, float norm, WorkType workType,
                              Department department, WorkingPlan workingPlan) {
        this.id = id;
        this.norm = norm;
        this.workType = workType;
        this.department = department;
        this.workingPlan = workingPlan;
    }

    public float getNorm() {
        return norm;
    }

    public void setNorm(float norm) {
        this.norm = norm;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public WorkingPlan getWorkingPlan() {
        return workingPlan;
    }

    public void setWorkingPlan(WorkingPlan workingPlan) {
        this.workingPlan = workingPlan;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
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
        final DiplomaPreparation other = (DiplomaPreparation) obj;
        return this.id == other.getId();
    }

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}