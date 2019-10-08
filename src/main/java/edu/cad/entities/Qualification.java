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
@Table(name = "qualification")
public class Qualification extends YearTracked implements IDatabaseEntity<Integer>, Serializable {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Expose
    @Column(name = "denotation")
    String denotation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "qualification")
    private Set<AcademicGroup> academicGroups = new HashSet<>(0);

    public Qualification() {
    }

    public Qualification(int id, String denotation) {
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
        int hash = 5;
        hash = 97 * hash + this.id;
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
        /*if (getClass() != obj.getClass()) {
            return false;
        }*/
        final Qualification other = (Qualification) obj;
        if (this.id != other.getId()) {
            return false;
        }
        return true;
    }


}
