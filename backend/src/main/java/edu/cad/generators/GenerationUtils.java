package edu.cad.generators;

import edu.cad.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Optional;

import static java.lang.String.format;

public class GenerationUtils {
    public static int extractCurriculumId(final Sheet sheet, final String curriculumIdMarker) {
        return getCellWhichHoldsCurriculumIdToken(sheet)
                .filter(GenerationUtils::isCellOfValidType)
                .map(GenerationUtils::toCurriculumIdTokenWithCleanUp)
                .filter(token -> token.contains(curriculumIdMarker))
                .map(token -> toCurriculumId(token, curriculumIdMarker))
                .orElseThrow(() -> new RuntimeException("Unable to get Curriculum id."));
    }

    private static Optional<Cell> getCellWhichHoldsCurriculumIdToken(final Sheet sheet) {
        return Optional.ofNullable(
                sheet.getRow(0).getCell(0) //start of the document
        );
    }

    private static boolean isCellOfValidType(Cell cell) {
        return cell.getCellType().equals(CellType.STRING);
    }

    private static String toCurriculumIdTokenWithCleanUp(Cell curriculumIdHolder) {
        final String curriculumIdToken = curriculumIdHolder.getStringCellValue();
        clearCell(curriculumIdHolder);
        return curriculumIdToken;
    }

    private static void clearCell(Cell curriculumIdHolder) {
        curriculumIdHolder.setCellType(CellType.BLANK); // also makes it empty
    }

    private static Integer toCurriculumId(String curriculumIdToken, String curriculumIdMarker) {
        final String id = curriculumIdToken.replaceFirst(curriculumIdMarker, StringUtils.EMPTY);
        if (Utils.isNumber(id)) {
            return Integer.valueOf(id);
        } else {
            throw new RuntimeException(format("Broken curriculum id token: <%s>. Should contain id of Curriculum.", curriculumIdToken));
        }
    }
}
