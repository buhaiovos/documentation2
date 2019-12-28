package edu.cad.study.load.other;

import edu.cad.entities.OtherLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherLoadRepository extends JpaRepository<OtherLoad, Integer> {
}
