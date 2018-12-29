package edu.cad.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import edu.cad.domain.StaffType;
import edu.cad.entities.interfaces.IDatabaseEntity;
import edu.cad.utils.k3.SourceOfFinancing;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "staff")
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"educationForm"})
public class Staff implements IDatabaseEntity {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Expose
    @Column(name = "first_name")
    private String firstName;

    @Expose
    @Column(name = "last_name")
    private String lastName;

    @Expose
    @Column(name = "middle_name")
    private String middleName;

    @Expose
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private StaffType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_education_form")
    private EducationForm educationForm;

    @Expose
    @Column(name = "degree")
    private String degree;

    @Expose
    @Column(name = "position")
    private String position;

    @Expose
    @Column(name = "rate")
    private double rate;

    @Expose
    @Column(name = "source_of_financing")
    @Enumerated(EnumType.STRING)
    private SourceOfFinancing sourceOfFinancing;

    public void setTypeFromString(String type) {
        this.type = StaffType.valueOf(type);
    }

    public void setSourceOfFinancingFromString(String name) {
        this.sourceOfFinancing = SourceOfFinancing.valueOf(name);
    }
}
