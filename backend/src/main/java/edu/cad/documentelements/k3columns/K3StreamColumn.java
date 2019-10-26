package edu.cad.documentelements.k3columns;

import edu.cad.utils.k3.K3SubjectEntity;
import edu.cad.utils.k3.TypeOfGroupWork;

public class K3StreamColumn extends AbstractK3Column{
    
    public K3StreamColumn(int columnNumber) {
        super(columnNumber);
    }

    @Override
    public String getValue(K3SubjectEntity subject) {
        if(subject.getSubgroup(TypeOfGroupWork.Academic) > 0)
            return "1";
        
        return "0";
    }
    
}
