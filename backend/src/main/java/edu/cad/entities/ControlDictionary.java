package edu.cad.entities;

import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
@Entity
@Table(name = "dict_control")
public class ControlDictionary extends YearTracked implements IDatabaseEntity<Integer>, Serializable {
    public static final int EXAM_ID = 1;
    public static final int CREDIT_ID = 2;
    public static final int MODULES_AND_TESTS_ID = 3;
    public static final int COURSE_PROJECT = 4;
    public static final int COURSE_WORK = 5;
    public static final int CALCULATION_GRAPHIC_ASSIGNMENT = 6;
    public static final int STATE_TEST = 7;
    public static final int ESSAY = 8;
    public static final int DIFFERENTIATED_CREDIT_ID = 9;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "denotation")
    private String denotation;

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
