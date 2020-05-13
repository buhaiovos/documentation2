package edu.cad.study.load.subject;

import edu.cad.entities.EducationForm;
import edu.cad.entities.SubjectInfo;
import edu.cad.entities.SubjectStudyLoad;
import edu.cad.utils.k3.SourceOfFinancing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectStudyLoadRepository extends JpaRepository<SubjectStudyLoad, Integer> {
    Optional<SubjectStudyLoad> findBySubjectInfoAndSourceOfFinancingAndEducationFormAndYearOfInformation(
            SubjectInfo info,
            SourceOfFinancing source,
            EducationForm educationForm,
            short yearOfInformation
    );
}
