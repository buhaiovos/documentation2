package edu.cad.documentelements.k3columns;

import edu.cad.documentelements.columns.AbstractColumn;
import edu.cad.entities.CurriculumSubject;
import edu.cad.utils.Utils;
import edu.cad.utils.k3.K3SubjectEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;

@Slf4j
public abstract class AbstractK3Column extends AbstractColumn{

    public  AbstractK3Column(int columnNumber){
        super(columnNumber);
    }

    @Override
    public void fill(Row row, CurriculumSubject record) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public abstract String getValue(K3SubjectEntity subject);

    public void fill(Row row, K3SubjectEntity subject, String value){
        value = getValue(subject);

        if (!Utils.isNumber(value)) {
            log.error("Value of cell is not numeric: {}", value);
            return;
        }

        double numericValue = Double.parseDouble(value);

        if(numericValue > 0) {
            this.fill(row, numericValue);
        } else {
            this.clear(row);
        }
    }
}
