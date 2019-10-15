package edu.cad.study.persistence;

import edu.cad.entities.StateCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateCertificationRepository extends JpaRepository<StateCertification, Integer> {
}
