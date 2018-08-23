package edu.cad.documentelements.columns;

import edu.cad.daos.HibernateDAO;
import edu.cad.entities.Control;
import edu.cad.entities.ControlDictionary;
import edu.cad.entities.CurriculumSubject;
import edu.cad.entities.Subject;
import edu.cad.utils.Utils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.apache.poi.ss.usermodel.Row;

public class ControlColumn extends AbstractColumn{
    private final ControlDictionary control;
    
    public ControlColumn(int columnNumber, ControlDictionary control) {
        super(columnNumber);
        this.control = control;
    }

    @Override
    public void fill(Row row, CurriculumSubject record) {
        Set<Control> controls = new TreeSet<>();

        controls.addAll(getSubjectControls(record, control));

        if(control.getId() == 2){
            ControlDictionary diff = new HibernateDAO<>(ControlDictionary.class).get(9);
            controls.addAll(getSubjectControls(record, diff));
        }

        writeControls(row, controls);
    }
    
    public Set<Control> getSubjectControls(CurriculumSubject record, ControlDictionary type){
        Set<Control> result = new HashSet<>();
        
        for(Subject element : record.getSubject().getSubSubjects(record.getCurriculum())){
            result.addAll(element.getControlsByType(type));
        }
        
        return result;
    }
    
    private void writeControls(Row row, Set<Control> controls){
        if(controls.isEmpty())
            return;
        
        Iterator<Control> iterator = controls.iterator();

        StringBuilder value = new StringBuilder();
        value.append(iterator.next().toString());
            
        if(controls.size() == 1 && Utils.isParseable(value.toString())){
            fill(row, Integer.parseInt(value.toString()));
            return;
        }
            
        while(iterator.hasNext()){
            value.append(",");
            value.append(iterator.next().toString());
        }

        fill(row, value.toString());
    }
}
