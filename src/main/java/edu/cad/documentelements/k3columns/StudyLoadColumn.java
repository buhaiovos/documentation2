package edu.cad.documentelements.k3columns;

import edu.cad.utils.k3.K3SubjectEntity;

public class StudyLoadColumn extends AbstractK3Column {
    
    private final StudyLoadType type;
    
    public StudyLoadColumn(int columnNumber, StudyLoadType type) {
        super(columnNumber);
        this.type = type;
    }
    
    public StudyLoadType getColumnType() {
        return this.type;
    }

    @Override
    public String getValue(K3SubjectEntity subject) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
