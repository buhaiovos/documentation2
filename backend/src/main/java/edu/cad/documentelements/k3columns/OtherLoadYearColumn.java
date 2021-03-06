package edu.cad.documentelements.k3columns;

import edu.cad.entities.OtherLoadInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class OtherLoadYearColumn extends AbstractOtherLoadColumn {
    public OtherLoadYearColumn(int semester, int columnNumber) {
        super(semester, columnNumber);
    }

    @Override
    public void fill(Row row, OtherLoadInfo info) {
        Cell cell = row.getCell(columnNumber);
        cell.setCellValue(info.getYearOfEducation());
    }
}
