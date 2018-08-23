package edu.cad.documentelements.controlcounters;

import edu.cad.daos.HibernateDAO;
import edu.cad.documentelements.AbstractDocumentElement;
import edu.cad.entities.ControlDictionary;
import edu.cad.entities.Curriculum;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class ControlCounter extends AbstractDocumentElement{
    protected final Cell cell;
    protected final ControlDictionary control;
    
    public ControlCounter(Cell cell, ControlDictionary control) {
        this.cell = cell;
        this.control = control;
    }

    public void fill(Curriculum curriculum) {
        StringBuilder value = new StringBuilder();
        int count = curriculum.countControlsByType(control);
        
        if(control.getId() == 2){
            ControlDictionary diff = new HibernateDAO<>(ControlDictionary.class).get(9); 
            int diffCount = curriculum.countControlsByType(diff);
            
            if(diffCount > 0){
                value.append(count);
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
    
    public void clear(){
        cell.setCellType(CellType.BLANK);
    }
}
