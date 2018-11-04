package edu.cad.utils.documentutils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class K3SemesterStartRowFinder {
    
    private static final String TOKEN_START = "#sem";
    
    public static Row findSemesterStartRow(Sheet sheet, int semesterNum) {
        validateNum(semesterNum);
        return findRow(sheet, Integer.toString(semesterNum));
    }

    private static void validateNum(int semesterNum) {
        if ((semesterNum > 2) || (semesterNum < 1)) {
            throw new IllegalArgumentException("wrong semester number: " + semesterNum);
        }
    }

    private static Row findRow(Sheet sheet, String num) {
        final String token = TOKEN_START + num + "_";

        for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row currentRow = sheet.getRow(rowIndex);
            for (int columnIndex = 0; columnIndex <= currentRow.getLastCellNum(); columnIndex++) {
                Cell currentCell = currentRow.getCell(columnIndex);
                String content = CellWithTokenValidator.getContentIfCellValid(currentCell, token);
                if ((content != null) && content.equals(token)) {
                    currentCell.setCellValue(1);
                    return currentRow;
                }
            }
        }
        return null;
    }
}
