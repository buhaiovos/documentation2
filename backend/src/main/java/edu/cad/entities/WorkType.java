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
@Table(name = "type_of_work")
public class WorkType extends YearTracked implements IDatabaseEntity<Integer>, Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "denotation")
    private String denotation;

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
