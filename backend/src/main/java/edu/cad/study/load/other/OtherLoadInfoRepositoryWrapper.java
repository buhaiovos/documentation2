package edu.cad.study.load.other;

import edu.cad.entities.EducationForm;
import edu.cad.entities.OtherLoad;
import edu.cad.entities.OtherLoadInfo;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.utils.k3.SourceOfFinancing;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OtherLoadInfoRepositoryWrapper extends YearAwareRepositoryWrapper<OtherLoadInfo, Integer> {
    private final YearProvider yearProvider;
    private final OtherLoadInfoRepository repo;

    public OtherLoadInfoRepositoryWrapper(OtherLoadInfoRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
        this.repo = repo;
        this.yearProvider = yearProvider;
    }

    public Optional<OtherLoadInfo> findByLoadHeaderAndSemesterAndYearAndEducationFormAndSourceOfFinancing(OtherLoad loadHeader,
                                                                                                          int semester,
                                                                                                          int yearOfEducation,
                                                                                                          EducationForm educationForm,
                                                                                                          SourceOfFinancing source) {
        return repo.findByLoadHeaderAndSemesterAndYearOfEducationAndEducationFormAndSourceOfFinancingAndYearOfInformation(
                loadHeader, semester, yearOfEducation, educationForm, source, yearProvider.getCurrent()
        );
    }

    public Optional<OtherLoadInfo> findByLoadHeaderAndSemesterAndEducationFormAndSourceOfFinancing(OtherLoad loadHeader,
                                                                                                   int semester,
                                                                                                   EducationForm educationForm,
                                                                                                   SourceOfFinancing source) {
        return repo.findByLoadHeaderAndSemesterAndEducationFormAndSourceOfFinancingAndYearOfInformation(
                loadHeader, semester, educationForm, source, yearProvider.getCurrent()
        );
    }

}
