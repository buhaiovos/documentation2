package edu.cad.documentelements.k3columns;

import edu.cad.utils.documentutils.CellWithTokenValidator;
import edu.cad.utils.documentutils.ColumnTokenStringSplitter;
import org.apache.poi.ss.usermodel.Cell;

import static edu.cad.utils.documentutils.ColumnTokenStringSplitter.K3_ST_LOAD_TOKEN_BEGINNING;

public class StudyLoadColumnFactory {
    public static StudyLoadColumn createColumnAndSetFormula(Cell cell) {
        if (cell != null) {
            String cellContent = CellWithTokenValidator.getContentIfCellValid(cell, K3_ST_LOAD_TOKEN_BEGINNING);
            if (cellContent != null) {
                ColumnTokenStringSplitter ctss = new ColumnTokenStringSplitter(cellContent);
                cell.setCellFormula(ctss.getFormula());
                
                int columnIndex = cell.getColumnIndex();
                StudyLoadType type = StudyLoadType.getByToken(ctss.getType());
                
                return new StudyLoadColumn(columnIndex, type);
            }      
        }  
        return null;
    }
    
}
