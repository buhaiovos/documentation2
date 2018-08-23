package edu.cad.documentelements.areas;

import edu.cad.documentelements.AbstractDocumentElement;
import edu.cad.entities.Curriculum;
import org.apache.poi.ss.usermodel.Sheet;

public abstract class AbstractDocumentArea extends AbstractDocumentElement {
    protected Sheet sheet;
    protected int rowNumber;
    
    public AbstractDocumentArea(Sheet sheet) {
        this.sheet = sheet;
    }
    
    public AbstractDocumentArea(Sheet sheet, String token, int startRow) {
        this.sheet = sheet;
        findRowNumber(startRow, token);
    }
    
    public abstract void fill(Curriculum curriculum);
    
    protected boolean findRowNumber(int startRow, String token){
        int columnNumber;
        
        do{
            columnNumber = findInRow(sheet.getRow(startRow), token);
            
            if(columnNumber != -1){
                rowNumber = startRow;
                return true;
            }
            
            startRow++;
        } while(startRow < sheet.getLastRowNum());
        
        return false;
    }
    
    public int getRowNumer() {
        return rowNumber;
    }
}
