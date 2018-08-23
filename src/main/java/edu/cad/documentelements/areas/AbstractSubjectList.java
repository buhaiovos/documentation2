package edu.cad.documentelements.areas;

import edu.cad.documentelements.DocumentSection;
import edu.cad.documentelements.columns.AbstractColumn;
import edu.cad.documentelements.columns.ColumnFactory;
import edu.cad.documentelements.columns.TitleColumn;
import edu.cad.documentelements.controlcounters.ControlCounter;
import edu.cad.documentelements.controlcounters.ControlCounterFactory;
import edu.cad.entities.Curriculum;
import edu.cad.entities.CurriculumSubject;
import edu.cad.entities.Section;
import edu.cad.entities.SubjectDictionary;
import edu.cad.utils.documentutils.RowInserter;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public abstract class AbstractSubjectList extends AbstractDocumentArea {
    protected final Set<AbstractColumn> columns;
    protected final Set<ControlCounter> counters;
    
    public AbstractSubjectList(Sheet sheet, int startRow) {
        super(sheet, "#section", startRow);
        columns = new HashSet<>();
        addColumns(); 
        counters = new HashSet<>();
        addCounters();
    }
    
    protected void fill(Curriculum curriculum, SubjectSection subjectSection) {
        Set<CurriculumSubject> records = new TreeSet<>();
        records.addAll(curriculum.getCurriculumSubjects());
        
        while(true){
            DocumentSection documentSection = new DocumentSection(sheet, rowNumber);

            if(documentSection.getRowNumber() < 0)
                break;
            
            rowNumber = documentSection.getRowNumber();
            Section section = documentSection.getSection();
           
            fillSection(section, records, subjectSection);
        }
        
        for(ControlCounter counter : counters){
            counter.fill(curriculum);
        }
    }
    
     private void addColumns(){
        Row row = sheet.getRow(rowNumber);
        
        for(int i = 0; i < row.getLastCellNum(); i++){
            AbstractColumn column = ColumnFactory.createColumn(row.getCell(i));
            
            if(column != null){
                columns.add(column);  
                
                if(!(column instanceof TitleColumn)){
                    column.clear(row);
                }
            }
        }
    }
    
    private void addCounters(){
        for(int i = 0; i < sheet.getLastRowNum(); i++){
            Row row = sheet.getRow(i);
            
            if(row == null)
                continue;
            
            for(int j = 0; j < row.getLastCellNum(); j++){
                ControlCounter counter = ControlCounterFactory.getControlCounter(row.getCell(j));
                if(counter != null){
                    counter.clear();
                    counters.add(counter);
                }
            }  
        }
    }
    
    private void fillSection(Section section, Set<CurriculumSubject> records,
            SubjectSection subjectSection){
        boolean first = true;

        for(CurriculumSubject record : records){    
            SubjectDictionary subject = record.getSubject().getSubject();
            
            if(!subjectSection.getSection(subject).equals(section))
                continue;

            if(!first){
                RowInserter.insertRow(sheet, rowNumber);
            } else {
                first = false;
            }
            
            for(AbstractColumn column : columns){
                column.fill(sheet.getRow(rowNumber), record);
            }
            //REMOVE
            HSSFFormulaEvaluator.evaluateAllFormulaCells(sheet.getWorkbook());
            rowNumber++;
        }
        
        if(first){
            rowNumber++;
        }
    }
    
    protected interface SubjectSection{
        public Section getSection(SubjectDictionary subject);
    }
}
