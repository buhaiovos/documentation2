package edu.cad.documentelements.k3columns;

import edu.cad.utils.k3.K3SubjectEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DummyColumn extends AbstractK3Column {

    public DummyColumn(int columnNumber) {
        super(columnNumber);
        log.info("creating dummy column with index {}", columnNumber);
    }

    @Override
    public String getValue(K3SubjectEntity subject) {
        return "";
    }
}
