package edu.cad.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false, of = "id")
@Accessors(chain = true)
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

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
