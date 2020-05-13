package edu.cad.documentelements.columns;

import edu.cad.documentelements.AbstractDocumentElement;
import edu.cad.entities.CurriculumSubject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public abstract class AbstractColumn extends AbstractDocumentElement {
    protected int columnNumber;

    public AbstractColumn() {
    }

    public AbstractColumn(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public AbstractColumn(Row row, String token) {
        columnNumber = findInRow(row, token);
    }

    public abstract void fill(Row row, CurriculumSubject record);

    public void fill(Row row, String value) {
        Cell cell = row.getCell(columnNumber);
        cell.setCellValue(value);
    }

    public void fill(Row row, double value) {
        Cell cell = row.getCell(columnNumber);
        cell.setCellValue(value);
    }

    public double getNumericCellValue(Row row) {
        CellType cellType = row.getCell(columnNumber).getCellType();
        if (!CellType.NUMERIC.equals(cellType)) {
            return -1;
        }

        return row.getCell(columnNumber).getNumericCellValue();
    }

    public void clear(Row row) {
        row.getCell(columnNumber).setBlank();
    }

    public int getColumnNumber() {
        return columnNumber;
    }
}
