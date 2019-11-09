package edu.cad.documentelements.k3columns;

import edu.cad.domain.StudyLoadType;
import edu.cad.entities.SubjectStudyLoad;
import edu.cad.utils.documentutils.CellWithTokenValidator;
import edu.cad.utils.documentutils.ColumnTokenStringSplitter;
import org.apache.poi.ss.usermodel.Cell;

import java.util.function.BiConsumer;

import static edu.cad.utils.documentutils.ColumnTokenStringSplitter.K3_ST_LOAD_TOKEN_BEGINNING;

class SubjectStudyLoadColumnsFactory {
    static SubjectStudyLoadColumn createColumnAndSetFormula(Cell cell) {
        if (cell != null) {
            String cellContent = CellWithTokenValidator.getContentIfCellValid(cell, K3_ST_LOAD_TOKEN_BEGINNING);
            if (cellContent != null) {
                ColumnTokenStringSplitter ctss = new ColumnTokenStringSplitter(cellContent);
                cell.setCellFormula(ctss.getFormula());

                int columnIndex = cell.getColumnIndex();
                StudyLoadType type = StudyLoadType.getByToken(ctss.getType());

                return new SubjectStudyLoadColumn(columnIndex, type, getSetterMethodByType(type));
            }
        }
        return null;
    }

    @SuppressWarnings("DuplicatedCode")
    private static BiConsumer<SubjectStudyLoad, Double> getSetterMethodByType(StudyLoadType type) {
        return switch (type) {
            case LECTURES -> SubjectStudyLoad::setLectures;
            case PRACTICES -> SubjectStudyLoad::setPractices;
            case LABS -> SubjectStudyLoad::setLabs;
            case INDIVIDUALS -> SubjectStudyLoad::setIndividuals;
            case EXAMS -> SubjectStudyLoad::setExams;
            case CREDITS -> SubjectStudyLoad::setCredits;
            case CONTROL_WORKS -> SubjectStudyLoad::setControlWorks;
            case COURSE_PROJECTS -> SubjectStudyLoad::setCourseProjects;
            case COURSE_WORKS -> SubjectStudyLoad::setCourseWorks;
            case RGRS -> SubjectStudyLoad::setRGRs;
            case DKR -> SubjectStudyLoad::setDKRs;
            case REFERATS -> SubjectStudyLoad::setReferats;
            case CONSULTATIONS -> SubjectStudyLoad::setConsultations;
        };
    }
}
