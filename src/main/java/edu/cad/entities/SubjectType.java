package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subject_type")
public class SubjectType implements IDatabaseEntity, Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    private Set<SubjectHeader> subjects = new HashSet<>();

    public SubjectType() {
    }

    public SubjectType(int id, String denotation) {
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

    public Set<SubjectHeader> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SubjectHeader> subjects) {
        this.subjects.clear();
        this.subjects.addAll(subjects);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id;
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
        final SubjectType other = (SubjectType) obj;
        return this.id == other.getId();
    }
}
