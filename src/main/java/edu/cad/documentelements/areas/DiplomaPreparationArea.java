package edu.cad.documentelements.areas;

import edu.cad.daos.HibernateDAO;
import edu.cad.daos.IDAO;
import edu.cad.documentelements.columns.AbstractColumn;
import edu.cad.documentelements.columns.SimpleColumn;
import edu.cad.entities.Curriculum;
import edu.cad.entities.DiplomaPreparation;
import edu.cad.entities.WorkType;
import edu.cad.entities.Workplan;
import edu.cad.utils.Utils;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class DiplomaPreparationArea extends AbstractDocumentArea{
    private final Map<String, AbstractColumn> columns;
    
    public DiplomaPreparationArea(Sheet sheet, int startRow) {
        super(sheet);
        columns = new HashMap<>();
        initializeColumns();
    }

    @Override
    public void fill(Curriculum curriculum) {  
        while(findRowNumber(rowNumber, "#work")){
            fillWorkArea(curriculum);
        }
    }
    
    private void fillWorkArea(Curriculum curriculum){
        Row currentRow = sheet.getRow(rowNumber);
        int workColumnNumber = findInRow(currentRow, "#work");
        boolean filled = false;
           
        WorkType workType = getWorkType(currentRow, workColumnNumber);
        if(workType == null)
            return;
        
        for(DiplomaPreparation preparation : ((Workplan)curriculum).getDiplomaPreparations()){
            if(preparation.getWorkType().equals(workType)){
                fillColumns(preparation);
                filled = true;
                break;
            }
        }  
        
        if(!filled){
            columns.get("norm").clear(currentRow);
        }
    }
    
    private WorkType getWorkType(Row row, int columnNumber){
        IDAO<WorkType> workTypeDAO = new HibernateDAO(WorkType.class);
        
        String cellContent = row.getCell(columnNumber).getStringCellValue();
        String workId = cellContent.replaceAll("#work", "");
        
        if(!Utils.isParseable(workId))
            return null;

        return workTypeDAO.get(Integer.parseInt(workId));
    }
    
    private void initializeColumns(){
        Row row = sheet.getRow(rowNumber);
        
        columns.put("norm", new SimpleColumn(row, "#work"));
        columns.put("department", new SimpleColumn(row, "#work_department"));
        columns.put("budgetary", new SimpleColumn(row, "#work_budgetary"));
        columns.put("contract", new SimpleColumn(row, "#work_contract"));
    }
    
    private void fillColumns(DiplomaPreparation preparation){
        Row row = sheet.getRow(rowNumber);
        Workplan workplan = (Workplan)preparation.getWorkplan();
        
        columns.get("norm").fill(row, preparation.getNorm());
        columns.get("department").fill(row, preparation.getDepartment().getDenotation());
        columns.get("budgetary").fill(row, workplan.countBudgetaryStudents());
        columns.get("contract").fill(row, workplan.countContractStudents());
    }
}
