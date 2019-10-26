package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "form_of_education")
public class EducationForm extends YearTracked implements IDatabaseEntity<Integer>, Serializable {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Expose
    @Column(name = "denotation")
    private String denotation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "educationForm")
    private Set<AcademicGroup> academicGroups = new HashSet<>(0);

    public EducationForm(int id, String denotation) {
        this.id = id;
        this.denotation = denotation;
    }

    public EducationForm setAcademicGroups(Set<AcademicGroup> academicGroups) {
        this.academicGroups.clear();
        this.academicGroups.addAll(academicGroups);
        return this;
    }

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
