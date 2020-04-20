package edu.cad.entities;

import edu.cad.domain.StaffType;
import edu.cad.entities.interfaces.IDatabaseEntity;
import edu.cad.utils.k3.SourceOfFinancing;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "staff")
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Staff extends YearTracked implements IDatabaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "first_name")
    private String firstName;


    @Column(name = "last_name")
    private String lastName;


    @Column(name = "middle_name")
    private String middleName;


    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private StaffType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_education_form")
    private EducationForm educationForm;


    @Column(name = "degree")
    private String degree;


    @Column(name = "position")
    private String position;


    @Column(name = "rate")
    private double rate;


    @Column(name = "source_of_financing")
    @Enumerated(EnumType.STRING)
    private SourceOfFinancing sourceOfFinancing;

    public void setTypeFromString(String type) {
        this.type = StaffType.valueOf(type);
    }

    public void setSourceOfFinancingFromString(String name) {
        this.sourceOfFinancing = SourceOfFinancing.valueOf(name);
    }

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return StringUtils.join(List.of(firstName, lastName).toArray(), " ");
    }
}
