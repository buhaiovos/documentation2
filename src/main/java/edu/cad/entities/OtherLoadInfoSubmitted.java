package edu.cad.entities;

import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "other_load_info_submitted")
public class OtherLoadInfoSubmitted implements IDatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "other_load_info_id")
    private OtherLoadInfo targetLoad;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff assignedProfessor;

    @Column(name = "hours")
    private double amount;
}
