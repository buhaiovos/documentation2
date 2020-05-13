package edu.cad.entities;

import edu.cad.entities.interfaces.IDatabaseEntity;
import edu.cad.utils.k3.SourceOfFinancing;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "other_load_info")
public class OtherLoadInfo extends YearTracked implements IDatabaseEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "source_of_financing")
    @Enumerated(EnumType.STRING)
    private SourceOfFinancing sourceOfFinancing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_education_form")
    private EducationForm educationForm;

    @Column(name = "year")
    private int yearOfEducation;

    @Column(name = "faculty")
    private String facultyTitle;

    @Column(name = "semester")
    private int semester;

    @ManyToOne(optional = false)
    @JoinColumn(name = "load_id")
    private OtherLoad loadHeader;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "other_load_info_to_groups",
            joinColumns = {@JoinColumn(name = "info_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")}
    )
    private List<AcademicGroup> groups;
    @Column(name = "hours")
    private double calculatedHours;

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
