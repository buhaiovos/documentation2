package edu.cad.entities;

import edu.cad.domain.PracticeType;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
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

    public Practice() {
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.id;
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
        final Practice other = (Practice) obj;
        return this.id == other.getId();
    }

    @Override
    public String toString() {
        return type + " (" + semester + " семестр/" + weeks + " тижні(в))";
    }

    public void setDenotationFromString(String type) {
        this.type = PracticeType.valueOf(type);
    }

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
