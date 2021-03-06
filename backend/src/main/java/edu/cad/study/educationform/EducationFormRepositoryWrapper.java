package edu.cad.study.educationform;

import edu.cad.entities.EducationForm;
import edu.cad.study.YearAwareRepositoryWrapper;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class EducationFormRepositoryWrapper extends YearAwareRepositoryWrapper<EducationForm, Integer> {
    public EducationFormRepositoryWrapper(EducationFormRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
