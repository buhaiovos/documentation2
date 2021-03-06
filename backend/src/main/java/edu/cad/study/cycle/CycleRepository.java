package edu.cad.study.cycle;

import edu.cad.entities.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CycleRepository extends JpaRepository<Cycle, Integer> {
}
