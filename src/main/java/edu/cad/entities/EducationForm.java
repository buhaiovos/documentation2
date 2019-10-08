package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
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

    public EducationForm() {
    }

    public EducationForm(int id, String denotation) {
        this.id = id;
        this.denotation = denotation;
    }

    public String getDenotation() {
        return denotation;
    }

    public void setDenotation(String denotation) {
        this.denotation = denotation;
    }

    public Set<AcademicGroup> getAcademicGroups() {
        return academicGroups;
    }

    public void setAcademicGroups(Set<AcademicGroup> academicGroups) {
        this.academicGroups.clear();
        this.academicGroups.addAll(academicGroups);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        final EducationForm other = (EducationForm) obj;
        return this.id == other.getId();
    }

    @Override
    public String toString() {
        return denotation;
    }
}
