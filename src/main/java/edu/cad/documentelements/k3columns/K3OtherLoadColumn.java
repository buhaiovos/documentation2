package edu.cad.documentelements.k3columns;

import edu.cad.utils.k3.K3SubjectEntity;

public class K3OtherLoadColumn extends AbstractK3Column {

    public K3OtherLoadColumn(int columnNumber) {
        super(columnNumber);
    }

    @Override
    public String getValue(K3SubjectEntity subject) {
        throw new UnsupportedOperationException("This is not the method you are looking for...");
    }
}
