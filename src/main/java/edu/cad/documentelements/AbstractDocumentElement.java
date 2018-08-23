package edu.cad.documentelements;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public abstract class AbstractDocumentElement {
    
    public static final int RIGHTMOST_CELL_INDEX = 100;
    
    protected final int findInRow(Row row, String token) {
        int cellIndex = -1;
        
        if (row != null) {
            for (int i = 0; i < RIGHTMOST_CELL_INDEX; i++) {
                Cell cell;
                if ( (cell = row.getCell(i)) != null ) {
                    String cellValue = getStringFromCellIfPossible(cell);
                    if (cellValue != null && cellValue.contains(token)) {
                        cellIndex = i;
                        break;
                    }
                }
            }
        }
        return cellIndex;
    }

    private String getStringFromCellIfPossible(Cell cell) {
        if(cell.getCellTypeEnum().equals(CellType.STRING))
            return cell.getStringCellValue();
        
        return null;
    }
    
    
}
