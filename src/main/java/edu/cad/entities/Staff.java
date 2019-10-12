package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.domain.StaffType;
import edu.cad.entities.interfaces.IDatabaseEntity;
import edu.cad.utils.k3.SourceOfFinancing;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "staff")
@Getter
@Setter
@ToString
public class Staff extends YearTracked implements IDatabaseEntity<Integer> {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return StringUtils.join(List.of(firstName, lastName).toArray(), " ");
    }
}
