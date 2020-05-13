package edu.cad.study.load.other;

import edu.cad.entities.EducationForm;
import edu.cad.entities.OtherLoad;
import edu.cad.entities.OtherLoadInfo;
import edu.cad.utils.k3.SourceOfFinancing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtherLoadInfoRepository extends JpaRepository<OtherLoadInfo, Integer> {
    Optional<OtherLoadInfo> findByLoadHeaderAndSemesterAndYearOfEducationAndEducationFormAndSourceOfFinancingAndYearOfInformation(
            OtherLoad loadHeader, int semester, int yearOfEducation, EducationForm educationForm, SourceOfFinancing source, short yearOfInformation
    );

    Optional<OtherLoadInfo> findByLoadHeaderAndSemesterAndEducationFormAndSourceOfFinancingAndYearOfInformation(
            OtherLoad loadHeader, int semester, EducationForm educationForm, SourceOfFinancing source, short yearOfInformation
    );
}
