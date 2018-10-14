package edu.cad.generators;

import edu.cad.daos.HibernateDAO;
import edu.cad.documentelements.areas.CurriculumSubjectList;
import edu.cad.entities.Curriculum;
import edu.cad.utils.Utils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Optional;

import static java.lang.String.format;

class CurriculumGenerator extends DocumentGenerator {
    private static final int CURRICULUM_ID_ROW_NUM = 0;
    private static final int CURRICULUM_ID_CELL_NUM = 0;
    private static final String BLANK_STRING = "";
    private static final String CURRICULUM_ID_MARKER = "#curriculum_";

    @Override
    void fillInSheet(final Sheet sheet) {
        final int id = extractCurriculumId(sheet, CURRICULUM_ID_MARKER);
        final Curriculum curriculum = findCurriculum(id);
        fillCurriculumSheetWithSubjectList(sheet, curriculum);
    }

    int extractCurriculumId(final Sheet sheet, final String curriculumIdMarker) {
        return getCellWhichHoldsCurriculumIdToken(sheet)
                .filter(this::isCellOfValidType)
                .map(this::toCurriculumIdTokenWithCleanUp)
                .filter(token -> token.contains(curriculumIdMarker))
                .map(this::toCurriculumId)
                .orElseThrow(() -> new RuntimeException("Unable to get Curriculum id."));
    }

    private Optional<Cell> getCellWhichHoldsCurriculumIdToken(final Sheet sheet) {
        return Optional.ofNullable(
                sheet.getRow(CURRICULUM_ID_ROW_NUM).getCell(CURRICULUM_ID_CELL_NUM)
        );
    }

    private boolean isCellOfValidType(Cell cell) {
        return cell.getCellTypeEnum().equals(CellType.STRING);
    }

    private String toCurriculumIdTokenWithCleanUp(Cell curriculumIdHolder) {
        final String curriculumIdToken = curriculumIdHolder.getStringCellValue();
        clearCell(curriculumIdHolder);
        return curriculumIdToken;
    }

    private void clearCell(Cell curriculumIdHolder) {
        curriculumIdHolder.setCellType(CellType.BLANK); // also makes it empty
    }

    private Integer toCurriculumId(String curriculumIdToken) {
        final String id = curriculumIdToken.replaceFirst(CURRICULUM_ID_MARKER, BLANK_STRING);
        if (Utils.isNumber(id)) {
            return Integer.valueOf(id);
        } else {
            throw new RuntimeException(format("Broken curriculum id token: <%s>. Should contain id of Curriculum.", curriculumIdToken));
        }
    }

    private Curriculum findCurriculum(final int id) {
        return new HibernateDAO<>(Curriculum.class)
                .getById(id)
                .orElseThrow(() -> new RuntimeException(format("Curriculum with id <%d> is not found", id)));
    }

    private void fillCurriculumSheetWithSubjectList(final Sheet sheet, final Curriculum curriculum) {
        new CurriculumSubjectList(sheet, 0).fill(curriculum);
    }
}
