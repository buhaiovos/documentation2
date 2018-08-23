package edu.cad.generators;

import edu.cad.daos.HibernateDAO;
import edu.cad.documentelements.areas.CurriculumSubjectList;
import edu.cad.entities.Curriculum;
import edu.cad.utils.Utils;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class CurriculumGenerator implements IDocumentGenerator {
    
    protected Workbook template;
    
    public CurriculumGenerator(Workbook template) {
        this.template = template;
    }

    @Override
    public void generate() throws IOException {
        for(int i = 0; i < template.getNumberOfSheets(); i++){
            generateSheet(template.getSheetAt(i));
        }
    }
    
    @Override
    public Workbook getWorkbook(){
        return template;
    }
    
    protected int getCurriculumId(Sheet sheet, String token){
        String value;
        Cell cell = sheet.getRow(0).getCell(0);
        
        if(cell == null || !cell.getCellTypeEnum().equals(CellType.STRING))
            return 0;
        
        value = sheet.getRow(0).getCell(0).getStringCellValue();
        cell.setCellType(CellType.BLANK);
        if(!value.contains(token))
            return 0;
        
        value = value.substring(token.length() + 1);
        if(!Utils.isParseable(value))
            return 0;
        
        return Integer.parseInt(value);      
    }
    
    private void generateSheet(Sheet sheet) {
        int id = getCurriculumId(sheet, "#curriculum");
        CurriculumSubjectList subjectList = new CurriculumSubjectList(sheet, 0);       
        Curriculum curriculum = new HibernateDAO<>(Curriculum.class).get(id);
        
        if(curriculum == null)
            return;

        subjectList.fill(curriculum);
    }
}
