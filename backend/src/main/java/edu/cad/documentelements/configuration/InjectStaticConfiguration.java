package edu.cad.documentelements.configuration;

import edu.cad.documentelements.DocumentSection;
import edu.cad.documentelements.areas.DiplomaPreparationArea;
import edu.cad.documentelements.columns.ColumnFactory;
import edu.cad.documentelements.k3columns.K3WPColumnsFactory;
import edu.cad.study.control.dictionary.ControlDictionaryService;
import edu.cad.study.section.SectionService;
import edu.cad.study.subject.info.SubjectInfoService;
import edu.cad.study.worktype.WorkTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InjectStaticConfiguration {
    ControlDictionaryService cds;
    SectionService ss;
    WorkTypeService wts;
    SubjectInfoService sis;

    @PostConstruct
    public void performInjection() {
        ColumnFactory.setControlDictionaryService(cds);
        DocumentSection.setSectionService(ss);
        DiplomaPreparationArea.setWorkTypeService(wts);
        K3WPColumnsFactory.setControlDictionaryService(cds);
        K3WPColumnsFactory.setSubjectInfoService(sis);
    }
}
