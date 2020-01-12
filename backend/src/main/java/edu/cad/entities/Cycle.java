package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "id", callSuper = false)
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

    public void setSections(Set<Section> sections) {
        this.sections.clear();
        this.sections.addAll(sections);
    }

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
