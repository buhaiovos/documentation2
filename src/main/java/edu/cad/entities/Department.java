package edu.cad.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "department")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Department extends YearTracked implements IDatabaseEntity<Integer>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "denotation")
    private String denotation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    @JsonIgnore
    private Set<Specialization> specializations = new HashSet<>();

    public Department(int id, String denotation) {
        this.id = id;
        this.denotation = denotation;
    }

    public void setSpecializations(Set<Specialization> specializations) {
        this.specializations.clear();
        this.specializations.addAll(specializations);
    }
}
