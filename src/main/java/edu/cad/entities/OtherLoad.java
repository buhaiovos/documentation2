package edu.cad.entities;

import edu.cad.domain.ObjectOfWork;
import edu.cad.domain.OtherLoadType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "other_load")
public class OtherLoad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
}
