package edu.cad.study.persistence;

import edu.cad.entities.OtherLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherLoadRepository extends JpaRepository<OtherLoad, Integer> {
}
