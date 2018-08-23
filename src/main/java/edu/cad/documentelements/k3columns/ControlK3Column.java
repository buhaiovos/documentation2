package edu.cad.documentelements.k3columns;

import edu.cad.daos.HibernateDAO;
import edu.cad.entities.ControlDictionary;
import edu.cad.utils.k3.K3SubjectEntity;

public class ControlK3Column extends AbstractK3Column{
    private final ControlDictionary control;
    
    public ControlK3Column(int columnNumber, ControlDictionary control) {
        super(columnNumber);
        this.control = control;
    }

    @Override
    public String getValue(K3SubjectEntity subject) {
        if(control.getId() == 5){
            if(subject.getSubject().hasCourseWork()){
                return "3";
            }
        }
        
        if(subject.getSubject().hasControlOfType(control)){
            return "1";
        }
        
        if(control.getId() == 2){
            ControlDictionary diff = new HibernateDAO<>(ControlDictionary.class).get(9);
            if(subject.getSubject().hasControlOfType(diff)){
                return "1";
            }
        }
        
        return "0";
    }
    
}
