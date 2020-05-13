package edu.cad.entities;

import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "other_load_info_distributed")
public class DistributedOtherLoadInfo extends YearTracked implements IDatabaseEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "other_load_info_id")
    private OtherLoadInfo targetLoad;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff assignedProfessor;

    @Column(name = "hours")
    private double amount;

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
