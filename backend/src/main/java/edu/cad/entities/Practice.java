package edu.cad.entities;

import edu.cad.domain.PracticeType;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "practice")
public class Practice extends YearTracked implements IDatabaseEntity<Integer>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "semester")
    private int semester;

    @Column(name = "weeks")
    private int weeks;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PracticeType type;

    @Column(name = "start")
    private LocalDate start;

    @Column(name = "finish")
    private LocalDate finish;

    public Practice setStart(LocalDate start) {
        this.start = start;
        return this;
    }

    @Override
    public String toString() {
        return type + " (" + semester + " семестр/" + weeks + " тижні(в))";
    }

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
