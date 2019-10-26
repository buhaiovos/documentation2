package edu.cad.documentelements.columns;

import edu.cad.entities.SubjectHeader;
import org.apache.poi.ss.usermodel.Row;

public class TitleColumn extends AbstractColumn {

    public TitleColumn(int columnNumber) {
        super(columnNumber);
    }

    @Override
    public void fill(Row row, edu.cad.entities.CurriculumSubject record) {
        SubjectHeader subject = record.getSubjectInfo().getSubjectHeader();
        fill(row, subject.getDenotation());
    }

}
