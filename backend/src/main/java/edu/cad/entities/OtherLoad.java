package edu.cad.entities;

import edu.cad.domain.ObjectOfWork;
import edu.cad.domain.OtherLoadType;
import edu.cad.entities.interfaces.IDatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "other_load")
public class OtherLoad extends YearTracked implements IDatabaseEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "load_type")
    @Enumerated(EnumType.STRING)
    private OtherLoadType loadType;

    @Column(name = "object")
    @Enumerated(EnumType.STRING)
    private ObjectOfWork objectOfWork;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "loadHeader"
    )
    private List<OtherLoadInfo> loadInfo;

    @Override
    public String toString() {
        return id + " " + loadType.name() + " " + objectOfWork.name();
    }

    @Override
    public void setIdentifier(Integer id) {
        this.id = id;
    }
}
