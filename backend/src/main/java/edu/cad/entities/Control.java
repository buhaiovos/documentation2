package edu.cad.entities;

import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

import static edu.cad.entities.ControlDictionary.DIFFERENTIATED_CREDIT_ID;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "control")
public class Control extends YearTracked implements IDatabaseEntity<Integer>, Serializable, Comparable<Control> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;


    @Column(name = "semester")
    private int semester;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_type")
    private ControlDictionary type;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_subject")
    private SubjectInfo subjectInfo;

    @Override
    public int compareTo(Control other) {
        return this.semester - other.semester;
    }

    @Override
    public String toString() {
        return "" + type + " - " + semester;
    }

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }

    public String documentValue() {
        return type.getId() == DIFFERENTIATED_CREDIT_ID ?
                semester + "д" : Integer.toString(semester);
    }

}

