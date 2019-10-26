package edu.cad.study.persistence;

import edu.cad.entities.EducationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationFormRepository extends JpaRepository<EducationForm, Integer> {
}
