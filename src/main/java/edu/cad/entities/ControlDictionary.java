package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "dict_control")
public class ControlDictionary implements IDatabaseEntity, Serializable {
    public static final int EXAM_ID = 1;
    public static final int CREDIT_ID = 2;
    public static final int MODULES_AND_TESTS_ID = 3;
    public static final int COURSE_PROJECT = 4;
    public static final int COURSE_WORK = 5;
    public static final int CALCULATIONAL_GRAPHIC_ASSIGNMENT = 6;
    public static final int STATE_TEST = 7;
    public static final int ESSAY = 8;
    public static final int DIFFERENTIATED_CREDIT_ID = 9;

    @Expose
    @Id
    @GenericGenerator(
            name = "assigned-identity",
            strategy = "edu.cad.utils.hibernateutils.AssignedIdentityGenerator"
    )
    @GeneratedValue(generator = "assigned-identity")
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Expose
    @Column(name = "denotation")
    private String denotation;

    public ControlDictionary() {
    }

    public ControlDictionary(int id, String denotation) {
        this.id = id;
        this.denotation = denotation;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getDenotation() {
        return denotation;
    }

    public void setDenotation(String denotation) {
        this.denotation = denotation;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.id;
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
        final ControlDictionary other = (ControlDictionary) obj;
        if (this.id != other.getId()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ControlDictionary{denotation=" + denotation + '}';
    }


}
