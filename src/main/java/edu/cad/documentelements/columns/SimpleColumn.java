package edu.cad.documentelements.columns;

import edu.cad.entities.CurriculumSubject;
import org.apache.poi.ss.usermodel.Row;

public class SimpleColumn extends AbstractColumn{

    public SimpleColumn(Row row, String token) {
        super(row, token);
    }

    @Override
    public void fill(Row row, CurriculumSubject record) { }
    
}
