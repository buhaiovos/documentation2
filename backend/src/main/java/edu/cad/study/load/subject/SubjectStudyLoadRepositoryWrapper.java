package edu.cad.study.load.subject;

import edu.cad.entities.EducationForm;
import edu.cad.entities.SubjectInfo;
import edu.cad.entities.SubjectStudyLoad;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.utils.k3.SourceOfFinancing;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SubjectStudyLoadRepositoryWrapper extends YearAwareRepositoryWrapper<SubjectStudyLoad, Integer> {
    private final SubjectStudyLoadRepository repo;
    private final YearProvider yearProvider;

    public SubjectStudyLoadRepositoryWrapper(SubjectStudyLoadRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
        this.repo = repo;
        this.yearProvider = yearProvider;
    }

    public Optional<SubjectStudyLoad> findBySubjectInfoAndSourceOfFinancingAndFormOfAndEducationForm(SubjectInfo info,
                                                                                                     SourceOfFinancing source,
                                                                                                     EducationForm educationForm) {
        return repo.findBySubjectInfoAndSourceOfFinancingAndEducationFormAndYearOfInformation(
                info, source, educationForm, yearProvider.getCurrent()
        );
    }
}
