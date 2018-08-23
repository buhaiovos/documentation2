package edu.cad.documentelements.controlcounters;

import edu.cad.daos.HibernateDAO;
import edu.cad.entities.ControlDictionary;
import edu.cad.entities.Curriculum;
import org.apache.poi.ss.usermodel.Cell;

public class SemesterControlCounter extends ControlCounter{
    private final int semester; 
    
    public SemesterControlCounter(Cell cell, ControlDictionary control, int semester){
        super(cell, control);
        this.semester = semester;
    }

    @Override
    public void fill(Curriculum curriculum) {
        StringBuilder value = new StringBuilder();
        int count = curriculum.countControlsByType(semester, control);
        
        if(control.getId() == 2){
            ControlDictionary diff = new HibernateDAO<>(ControlDictionary.class).get(9); 
            int diffCount = curriculum.countControlsByType(semester, diff);
            
            if(diffCount > 0){
                value.append(diffCount);
                value.append('д');
                
                if(count > 0){
                    value.append('+');
                }
            } 
        }
        
        if(count > 0)
            value.append(count);
        
        if(value.length() > 0){
            cell.setCellValue(value.toString());
        }  
    }
    
    
}
