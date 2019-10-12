package edu.cad.entities;

import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "state_certification")
public class StateCertification extends YearTracked implements IDatabaseEntity<Integer>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "semester")
    private int semester;

    @Column(name = "form")
    private String form;

    @Column(name = "start")
    private LocalDate start;

    @Column(name = "finish")
    private LocalDate finish;

    public StateCertification() {
    }

    @Override
    public String toString() {
        return form + " (" + semester + " семестр)";
    }

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
