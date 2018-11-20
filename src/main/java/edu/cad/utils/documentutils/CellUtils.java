package edu.cad.utils.documentutils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public final class CellUtils {
    private CellUtils() {
    }

    public static double getValueFromFormulaCell(Cell cell) {
        if (cell.getCachedFormulaResultTypeEnum().equals(CellType.STRING)) {
            return 0;
        } else {
            return cell.getNumericCellValue();
        }
    }
}
