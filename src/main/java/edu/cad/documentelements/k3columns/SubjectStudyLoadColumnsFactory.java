package edu.cad.documentelements.k3columns;

import edu.cad.domain.StudyLoadType;
import edu.cad.entities.SubjectStudyLoad;
import edu.cad.utils.documentutils.CellWithTokenValidator;
import edu.cad.utils.documentutils.ColumnTokenStringSplitter;
import org.apache.poi.ss.usermodel.Cell;

import java.util.function.BiConsumer;

import static edu.cad.utils.documentutils.ColumnTokenStringSplitter.K3_ST_LOAD_TOKEN_BEGINNING;
import static java.lang.String.format;

public class SubjectStudyLoadColumnsFactory {
    public static SubjectStudyLoadColumn createColumnAndSetFormula(Cell cell) {
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

    private static BiConsumer<SubjectStudyLoad, Double> getSetterMethodByType(StudyLoadType type) {
        switch (type) {
            case LECTURES:
                return SubjectStudyLoad::setLectures;
            case PRACTICES:
                return SubjectStudyLoad::setPractices;
            case LABS:
                return SubjectStudyLoad::setLabs;
            case INDIVIDUALS:
                return SubjectStudyLoad::setIndividuals;
            case EXAMS:
                return SubjectStudyLoad::setExams;
            case CREDITS:
                return SubjectStudyLoad::setCredits;
            case CONTROL_WORKS:
                return SubjectStudyLoad::setControlWorks;
            case COURSE_PROJECTS:
                return SubjectStudyLoad::setCourseProjects;
            case COURSE_WORKS:
                return SubjectStudyLoad::setCourseWorks;
            case RGRS:
                return SubjectStudyLoad::setRGRs;
            case DKR:
                return SubjectStudyLoad::setDKRs;
            case REFERATS:
                return SubjectStudyLoad::setReferats;
            case CONSULTATIONS:
                return SubjectStudyLoad::setConsultations;

            default:
                throw new IllegalArgumentException(format("Unsupported Type of Study Load: %s", type.name()));
        }
    }
}
