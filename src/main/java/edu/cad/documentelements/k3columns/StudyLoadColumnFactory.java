package edu.cad.documentelements.k3columns;

import edu.cad.entities.StudyLoadResults;
import edu.cad.utils.documentutils.CellWithTokenValidator;
import edu.cad.utils.documentutils.ColumnTokenStringSplitter;
import org.apache.poi.ss.usermodel.Cell;

import java.util.function.BiConsumer;

import static edu.cad.utils.documentutils.ColumnTokenStringSplitter.K3_ST_LOAD_TOKEN_BEGINNING;
import static java.lang.String.format;

public class StudyLoadColumnFactory {
    public static StudyLoadColumn createColumnAndSetFormula(Cell cell) {
        if (cell != null) {
            String cellContent = CellWithTokenValidator.getContentIfCellValid(cell, K3_ST_LOAD_TOKEN_BEGINNING);
            if (cellContent != null) {
                ColumnTokenStringSplitter ctss = new ColumnTokenStringSplitter(cellContent);
                cell.setCellFormula(ctss.getFormula());

                int columnIndex = cell.getColumnIndex();
                StudyLoadType type = StudyLoadType.getByToken(ctss.getType());

                return new StudyLoadColumn(columnIndex, type, getSetterMethodByType(type));
            }
        }
        return null;
    }

    private static BiConsumer<StudyLoadResults, Double> getSetterMethodByType(StudyLoadType type) {
        switch (type) {
            case LECTURES:
                return StudyLoadResults::setLectures;
            case PRACTICES:
                return StudyLoadResults::setPractices;
            case LABS:
                return StudyLoadResults::setLabs;
            case INDIVIDUALS:
                return StudyLoadResults::setIndividuals;
            case EXAMS:
                return StudyLoadResults::setExams;
            case CREDITS:
                return StudyLoadResults::setCredits;
            case CONTROL_WORKS:
                return StudyLoadResults::setControlWorks;
            case COURSE_PROJECTS:
                return StudyLoadResults::setCourseProjects;
            case COURSEWORKS:
                return StudyLoadResults::setCourseWorks;
            case RGRS:
                return StudyLoadResults::setRGRs;
            case DKR:
                return StudyLoadResults::setDKRs;
            case REFERATS:
                return StudyLoadResults::setReferats;
            case CONSULTATIONS:
                return StudyLoadResults::setConsultations;

            default:
                throw new IllegalArgumentException(format("Unsupported Type of Study Load: %s", type.name()));
        }
    }
}
