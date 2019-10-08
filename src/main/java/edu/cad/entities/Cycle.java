package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "cycle")
public class Cycle extends YearTracked implements IDatabaseEntity<Integer>, Serializable {
    
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    
    @Expose
    @Column(name = "denotation")
    private String denotation;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cycle")
    private Set<Section> sections = new HashSet<>();

    public Cycle() {
    }

    public Cycle(int id, String denotation) {
        this.id = id;
        this.denotation = denotation;
    }

    public String getDenotation() {
        return denotation;
    }

    public void setDenotation(String denotation) {
        this.denotation = denotation;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections.clear();
        this.sections.addAll(sections);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.id;
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
        /*if (getClass() != obj.getClass()) {
            return false;
        }*/
        final Cycle other = (Cycle) obj;
        if (this.id != other.getId()) {
            return false;
        }
        return true;
    }   
}
