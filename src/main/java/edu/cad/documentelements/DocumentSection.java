package edu.cad.documentelements;

import edu.cad.daos.HibernateDAO;
import edu.cad.entities.Section;
import edu.cad.utils.Utils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

public class DocumentSection extends AbstractDocumentElement{
    private static final String SECTION_TOKEN = "#section_";
    
    private int rowNumber = -1;
    private Section section = null;
    
    public DocumentSection(Sheet sheet, int startRow){
        findSection(sheet, startRow);
    }

    private void findSection(Sheet sheet, int startRow) {
        int id = findId(sheet, startRow);
        setSection(id);
    }

    private int findId(Sheet sheet, int startRow) {
        final int LAST_ROW_NUM = sheet.getLastRowNum();
        int currentRow = startRow;
        
        int foundCellIndex = findInRow(sheet.getRow(currentRow), SECTION_TOKEN);
        while ( (foundCellIndex < 0) && (currentRow < LAST_ROW_NUM) ) {
            currentRow++;
            foundCellIndex = findInRow(sheet.getRow(currentRow), SECTION_TOKEN);
        }
        
        if (foundCellIndex > 0) {
            rowNumber = currentRow;
            
            Cell foundCell = sheet.getRow(rowNumber).getCell(foundCellIndex);
            return extractIdFromString(foundCell.getStringCellValue());
        } 
        else {
            return -1;
        }
    }
    
    private void setSection(int id) {
        if (id > 0) {
            section = new HibernateDAO<>(Section.class).get(id);
        } else {
            section = null;
        }
    }
    
    private int extractIdFromString(String stringCellValue) {
        String idStr = stringCellValue.replaceFirst(SECTION_TOKEN, "").trim();
        
        if(Utils.isParseable(idStr)){
            return Integer.parseInt(idStr);
        }
        
        return -1;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public Section getSection() {
        return section;
    }
}
