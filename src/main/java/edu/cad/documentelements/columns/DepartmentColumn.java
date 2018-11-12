package edu.cad.documentelements.columns;

import edu.cad.entities.CurriculumSubject;
import edu.cad.entities.SubjectHeader;
import org.apache.poi.ss.usermodel.Row;

public class DepartmentColumn extends AbstractColumn {
    
    public DepartmentColumn(int columnNumber) {
        super(columnNumber);
    }
    
    @Override
    public void fill(Row row, CurriculumSubject record) {
        SubjectHeader subject = record.getSubjectInfo().getSubjectHeader();
        fill(row, subject.getDepartment().getDenotation());
    }
    
}
