package edu.cad.study.academicgroup;

import edu.cad.entities.AcademicGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicGroupRepository extends JpaRepository<AcademicGroup, Integer> {
}
