package edu.cad.generators;

import edu.cad.daos.HibernateDAO;
import edu.cad.documentelements.k3columns.AbstractK3Column;
import edu.cad.documentelements.k3columns.AllK3ColumnsFactory;
import edu.cad.documentelements.k3columns.StudyLoadColumn;
import edu.cad.entities.Department;
import edu.cad.entities.EducationForm;
import edu.cad.utils.Utils;
import edu.cad.utils.documentutils.K3SemesterStartRowFinder;
import edu.cad.utils.documentutils.RowInserter;
import edu.cad.utils.k3.K3SubjectEntity;
import edu.cad.utils.k3.K3SubjectListCreator;
import edu.cad.utils.k3.SourceOfFinancing;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class FormK3Generator implements IDocumentGenerator{
    private final Department department;
    private final Workbook workbook;
    private EducationForm educationForm;
    private SourceOfFinancing source;
    
    public FormK3Generator(Workbook workbook){
        this.department = new HibernateDAO<>(Department.class).get(1);
        this.workbook = workbook;
    }
    
    @Override
    public void generate() throws IOException {
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            generateSheet(workbook.getSheetAt(i));
        }
    }
    
    private void generateSheet(Sheet sheet) {
        setEdFormAndSource(sheet, "#edform", "#source");
        
        List<K3SubjectEntity> firstSemSubjects = 
                K3SubjectListCreator.createList(educationForm, source, department, 1);
        Map<Class, List<AbstractK3Column>> columnsMap = getInitializedMap();
        
        Row firstSemRow = K3SemesterStartRowFinder.findSemesterStartRow(sheet, 1);
        
        addColumnsToMap(columnsMap, firstSemRow);
        fill(sheet, firstSemSubjects, columnsMap.get(AbstractK3Column.class),
                firstSemRow.getRowNum());

        List<K3SubjectEntity> secondSemSubjects = 
                K3SubjectListCreator.createList(educationForm, source, department, 2); 
        Row secondSemRow = K3SemesterStartRowFinder.findSemesterStartRow(sheet, 2);
        fill(sheet, secondSemSubjects, columnsMap.get(AbstractK3Column.class), 
                secondSemRow.getRowNum());
    }
    
    @Override
    public Workbook getWorkbook(){
        return null;
    }
    
    private Map<Class, List<AbstractK3Column>> getInitializedMap() {
        Map<Class, List<AbstractK3Column>> map = new HashMap<>();
        map.put(AbstractK3Column.class, new ArrayList<>());
        map.put(StudyLoadColumn.class, new ArrayList<>());
        return map;
    }

    private void addColumnsToMap(Map<Class, List<AbstractK3Column>> map, Row row) {
        for (int i = 0; i <= row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            AllK3ColumnsFactory.createAndAddColumn(map, cell, source);                    
        }
    }
    
    private void fill(Sheet sheet, List<K3SubjectEntity> subjects, 
            List<AbstractK3Column> columns, int rowNumber){
        
        boolean first = true;

        for(K3SubjectEntity subject : subjects){    
            if(!first){
                RowInserter.insertRow(sheet, rowNumber);
            } else {
                first = false;
            }
            
            for(AbstractK3Column column : columns){
                if(column != null)
                    column.fill(sheet.getRow(rowNumber), subject, "");
            }
            HSSFFormulaEvaluator.evaluateAllFormulaCells(sheet.getWorkbook());
            rowNumber++;
        }
    }
    
    private void setEdFormAndSource(Sheet sheet, String edFormToken, 
            String sourceToken){
        
        int id = getId(sheet, 0, 0, edFormToken);
        educationForm = new HibernateDAO<>(EducationForm.class).get(id);
        
        id = getId(sheet, 0, 1, sourceToken);
        source = SourceOfFinancing.values()[id];
    }
    
    private int getId(Sheet sheet, int row, int col, String token) {
        String value;
        Cell cell = sheet.getRow(row).getCell(col);
        
        if(cell == null || !cell.getCellTypeEnum().equals(CellType.STRING))
            return 0;
        
        value = sheet.getRow(row).getCell(col).getStringCellValue();
        cell.setCellType(CellType.BLANK);
        if(!value.contains(token))
            return 0;
        
        value = value.substring(token.length() + 1);
        if(!Utils.isParseable(value))
            return 0;
        
        return Integer.parseInt(value);
    }
    
}
