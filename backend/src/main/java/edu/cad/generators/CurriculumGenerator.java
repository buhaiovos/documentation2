package edu.cad.generators;

import edu.cad.documentelements.areas.CurriculumSubjectList;
import edu.cad.entities.Curriculum;
import edu.cad.study.curriculum.CurriculumService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
class CurriculumGenerator extends DocumentGenerator {
    private static final String CURRICULUM_ID_MARKER = "#curriculum_";

    private final CurriculumService curriculumService;

    @Override
    public void fillInSheet(final Sheet sheet) {
        final int id = GenerationUtils.extractCurriculumId(sheet, CURRICULUM_ID_MARKER);
        final Curriculum curriculum = findCurriculum(id);
        fillCurriculumSheetWithSubjectList(sheet, curriculum);
    }

    private Curriculum findCurriculum(final int id) {
        return curriculumService
                .findById(id)
                .orElseThrow(() -> new RuntimeException(format("Curriculum with id <%d> is not found", id)));
    }

    private void fillCurriculumSheetWithSubjectList(final Sheet sheet, final Curriculum curriculum) {
        new CurriculumSubjectList(sheet, 0).fill(curriculum);
    }
}
