package edu.cad.entities;

import edu.cad.domain.StudyLoadType;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "subject_study_load_distributed")
public class DistributedSubjectStudyLoad extends YearTracked implements IDatabaseEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
