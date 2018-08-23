package edu.cad.utils.documentutils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class CellWithTokenValidator {
    
    public static String getContentIfCellValid(Cell cell, 
            String tokenBeginning) {
        if (cell != null) {
            if (cell.getCellTypeEnum() == CellType.STRING) {
                String cellContent = cell.getStringCellValue();
                if ( (cellContent != null) 
                        && (cellContent.contains(tokenBeginning)) ) {
                    return cellContent;
                }
            }
        }
        return null;
    }
    
}
