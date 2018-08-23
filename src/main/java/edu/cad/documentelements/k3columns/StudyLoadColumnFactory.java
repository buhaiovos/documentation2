package edu.cad.documentelements.k3columns;

import edu.cad.utils.documentutils.CellWithTokenValidator;
import edu.cad.utils.documentutils.ColumnTokenStringSplitter;
import org.apache.poi.ss.usermodel.Cell;

public class StudyLoadColumnFactory {
    
    private static final String TOKEN_BEGINNING = 
            ColumnTokenStringSplitter.K3_ST_LOAD_TOKEN_BEGINNING;
    
    public static StudyLoadColumn createColumnAndSetFormula(Cell cell) {
        if (cell != null) {             
            String cellContent = 
                    CellWithTokenValidator.getContentIfCellValid(cell,
                            TOKEN_BEGINNING);
            if (cellContent != null) {
                ColumnTokenStringSplitter ctss = 
                        new ColumnTokenStringSplitter(cellContent);
                cell.setCellFormula(ctss.getFormula());
                
                int columnIndex = cell.getColumnIndex();
                StudyLoadType type = StudyLoadType.getByToken(ctss.getType());
                
                return new StudyLoadColumn(columnIndex, type);
            }      
        }  
        return null;
    }
    
}
