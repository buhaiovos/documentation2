package edu.cad.documentelements.k3columns;

import edu.cad.daos.HibernateDao;
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
            if (subject.getSubjectInfo().hasCourseWork()) {
                return "3";
            }
        }

        if (subject.getSubjectInfo().hasControlOfType(control)) {
            return "1";
        }
        
        if(control.getId() == 2){
            ControlDictionary diff = new HibernateDao<>(ControlDictionary.class).get(9);
            if (subject.getSubjectInfo().hasControlOfType(diff)) {
                return "1";
            }
        }
        
        return "0";
    }
    
}
