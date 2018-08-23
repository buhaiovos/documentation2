package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "diploma_preparation")
public class DiplomaPreparation implements IDatabaseEntity, Serializable{
    
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
    private Workplan workplan;

    public DiplomaPreparation() {
    }

    public DiplomaPreparation(int id, float norm, WorkType workType, 
            Department department, Workplan workplan) {
        this.id = id;
        this.norm = norm;
        this.workType = workType;
        this.department = department;
        this.workplan = workplan;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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

    public Workplan getWorkplan() {
        return workplan;
    }

    public void setWorkplan(Workplan workplan) {
        this.workplan = workplan;
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
        if (this.id != other.getId()) {
            return false;
        }
        return true;
    }
}
