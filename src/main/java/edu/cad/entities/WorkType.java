package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
@Entity
@Table(name = "type_of_work")
public class WorkType extends YearTracked implements IDatabaseEntity<Integer>, Serializable {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Expose
    @Column(name = "denotation")
    private String denotation;

    public WorkType() {
    }

    public String getDenotation() {
        return denotation;
    }

    public void setDenotation(String denotation) {
        this.denotation = denotation;
    }

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
