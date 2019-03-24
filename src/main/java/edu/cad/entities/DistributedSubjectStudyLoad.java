package edu.cad.entities;

import edu.cad.domain.StudyLoadType;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "subject_study_load_distributed")
public class DistributedSubjectStudyLoad implements IDatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_load_id")
    private SubjectStudyLoad targetLoad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff assignedProfessor;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private StudyLoadType studyLoadType;

    @Column(name = "value")
    private double amount;
}
