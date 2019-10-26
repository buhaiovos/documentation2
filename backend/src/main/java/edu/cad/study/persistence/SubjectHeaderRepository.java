package edu.cad.study.persistence;

import edu.cad.entities.SubjectHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectHeaderRepository extends JpaRepository<SubjectHeader, Integer> {
}
