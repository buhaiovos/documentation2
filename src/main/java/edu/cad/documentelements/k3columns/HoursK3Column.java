package edu.cad.documentelements.k3columns;

import edu.cad.entities.interfaces.SubjectProperty;
import edu.cad.utils.k3.K3SubjectEntity;

public class HoursK3Column extends AbstractK3Column{
    private final SubjectProperty property;
    
    public HoursK3Column(int columnNumber, SubjectProperty property) {
        super(columnNumber);
        this.property = property;
    }

    @Override
    public String getValue(K3SubjectEntity subject) {
        return Double.toString(property.getValue(subject.getSubject()));
        //fill(row, property.getValue(subject));
    }
}
