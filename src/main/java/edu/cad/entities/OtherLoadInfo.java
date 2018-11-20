package edu.cad.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "other_load_info")
public class OtherLoadInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "year")
    private int yearOfEducation;

    @Column(name = "faculty")
    private String facultyTitle;

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
}
