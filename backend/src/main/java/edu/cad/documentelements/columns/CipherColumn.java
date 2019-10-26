package edu.cad.documentelements.columns;

import edu.cad.entities.CurriculumSubject;
import org.apache.poi.ss.usermodel.Row;

public class CipherColumn extends AbstractColumn {
    
    public CipherColumn(int columnNumber) {
        super(columnNumber);
    }

    @Override
    public void fill(Row row, CurriculumSubject record) {
         fill(row, record.getCipher());
    }
}
