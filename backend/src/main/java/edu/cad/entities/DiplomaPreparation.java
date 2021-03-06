package edu.cad.entities;

import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "diploma_preparation")
public class DiplomaPreparation extends YearTracked implements IDatabaseEntity<Integer>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "norm")
    private float norm;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_type_of_work")
    private WorkType workType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_department")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curriculum")
    private WorkingPlan workingPlan;

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "%s - %.2f (%s)"
                .formatted(workType.getDenotation(), norm, department.getDenotation());
    }
}
