package edu.cad.documentelements.areas.k3;

import edu.cad.documentelements.AbstractDocumentElement;
import edu.cad.documentelements.k3columns.AbstractOtherLoadColumn;
import edu.cad.entities.EducationForm;
import edu.cad.utils.k3.SourceOfFinancing;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType.STRING;

public abstract class K3OtherStudyLoadArea extends AbstractDocumentElement {
    private static final int SEARCH_START_ROW = 3;

    Map<String, Row> tokenToRow = new HashMap<>();
    Map<Integer, List<AbstractOtherLoadColumn>> semesterNumToColumns;
    Set<String> acceptableTokens;

    public K3OtherStudyLoadArea(Map<Integer, List<AbstractOtherLoadColumn>> semesterNumToColumns) {
        this.semesterNumToColumns = semesterNumToColumns;
    }

    public abstract void fill(Sheet sheet, EducationForm formOfEducation, SourceOfFinancing sourceOfFinancing);

    void findRowsOnSheet(Sheet sheet) {
        Objects.requireNonNull(acceptableTokens, "Area must have distinctive tokens.");

        for (int rowNum = SEARCH_START_ROW; rowNum < sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                Cell cell = row.getCell(cellNum);
                if (cell != null && cell.getCellTypeEnum().equals(STRING) && cell.getStringCellValue() != null) {
                    String value = cell.getStringCellValue();
                    if (acceptableTokens.contains(value)) {
                        tokenToRow.put(value, row);
                        cell.setCellType(BLANK);
                    }
                }
            }
        }
    }
}
