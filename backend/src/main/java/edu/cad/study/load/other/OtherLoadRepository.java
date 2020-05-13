package edu.cad.study.load.other;

import edu.cad.domain.ObjectOfWork;
import edu.cad.domain.OtherLoadType;
import edu.cad.entities.OtherLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtherLoadRepository extends JpaRepository<OtherLoad, Integer> {
    Optional<OtherLoad> findByLoadTypeAndObjectOfWorkAndYearOfInformation(OtherLoadType loadType,
                                                                          ObjectOfWork objectOfWork,
                                                                          short yearOfInformation);
}
