package edu.cad.documentelements.columns;

import edu.cad.entities.Curriculum;
import edu.cad.entities.CurriculumSubject;
import edu.cad.entities.SubjectInfo;
import edu.cad.entities.interfaces.SubjectProperty;
import org.apache.poi.ss.usermodel.Row;

public class HoursColumn extends AbstractColumn {
    private final SubjectProperty property;
    
    public HoursColumn(int columnNumber, SubjectProperty property) {
        super(columnNumber);
        this.property = property;
    }

    @Override
    public void fill(Row row, CurriculumSubject record) {
        final SubjectInfo subjectDetails = record.getSubjectInfo();
        final Curriculum curriculum = record.getCurriculum();

        double value = 0;
        for (SubjectInfo child : subjectDetails.getSubSubjects(curriculum)) {
            value += property.getValue(child);
        }

        if (value > 0) {
            fill(row, value);
        }
    }
}
