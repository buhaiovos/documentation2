package edu.cad.documentelements;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public abstract class AbstractDocumentElement {

    public static final int RIGHTMOST_CELL_INDEX = 100;

    @SuppressWarnings("ConstantConditions")
    protected final int findInRow(Row row, String token) {
        int columnIndex = -1;

        if (row != null) {
            for (int currentColumn = 0; currentColumn < RIGHTMOST_CELL_INDEX; currentColumn++) {
                Cell cell;
                if ((cell = row.getCell(currentColumn)) != null) {
                    String cellValue = getStringFromCellIfPossible(cell);
                    if (cellValue != null && cellValue.contains(token)) {
                        columnIndex = currentColumn;
                        break;
                    }
                }
            }
        }
        return columnIndex;
    }

    private String getStringFromCellIfPossible(Cell cell) {
        if (cell.getCellType().equals(CellType.STRING)) {
            return cell.getStringCellValue();
        }
        return null;
    }
}
