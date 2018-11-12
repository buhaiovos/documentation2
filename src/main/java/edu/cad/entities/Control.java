package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "control")
public class Control implements IDatabaseEntity, Serializable, 
        Comparable<Control>{
    
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

    public Control(int id, int semester, ControlDictionary type, SubjectInfo subjectInfo) {
        this.id = id;
        this.semester = semester;
        this.type = type;
        this.subjectInfo = subjectInfo;
    }
    
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
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
        if(type.getId() == 9)
            return semester + "д";
        
        return Integer.toString(semester);
    }
    
    
}
