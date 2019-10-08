package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "control")
public class Control extends YearTracked implements IDatabaseEntity<Integer>, Serializable, Comparable<Control> {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Expose
    @Column(name = "semester")
    private int semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type")
    private ControlDictionary type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subject")
    private SubjectInfo subjectInfo;

    public Control() {
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public ControlDictionary getType() {
        return type;
    }

    public void setType(ControlDictionary type) {
        this.type = type;
    }

    public SubjectInfo getSubjectInfo() {
        return subjectInfo;
    }

    public void setSubjectInfo(SubjectInfo subjectInfo) {
        this.subjectInfo = subjectInfo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Control)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        final Control other = (Control) obj;
        return this.id == other.id;
    }

    @Override
    public int compareTo(Control other) {
        return this.semester - other.semester;
    }

    @Override
    public String toString() {
        if (type.getId() == 9)
            return semester + "д";

        return Integer.toString(semester);
    }
}
