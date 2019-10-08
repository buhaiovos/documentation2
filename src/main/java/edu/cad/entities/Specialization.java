package edu.cad.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "specialization")
public class Specialization extends YearTracked implements IDatabaseEntity<Integer>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "denotation")
    private String denotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_department")
    @JsonIgnore
    private Department department;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "specialization")
    @JsonIgnore
    private Set<AcademicGroup> academicGroups = new HashSet<>();

    public void setAcademicGroups(Set<AcademicGroup> academicGroups) {
        this.academicGroups.clear();
        this.academicGroups.addAll(academicGroups);
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        /*if (getClass() != obj.getClass()) {
            return false;
        }*/
        final Specialization other = (Specialization) obj;
        if (this.id != other.getId()) {
            return false;
        }
        return true;
    }
}
