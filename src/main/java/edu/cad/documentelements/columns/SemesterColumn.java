package edu.cad.documentelements.columns;

import edu.cad.entities.CurriculumSubject;
import edu.cad.entities.Subject;
import edu.cad.entities.interfaces.SubjectProperty;
import org.apache.poi.ss.usermodel.Row;

public class SemesterColumn extends AbstractColumn{
    private final int semester;
    private final int weeks;
    private final SubjectProperty property;
    
    public SemesterColumn(int columnNumber, int semester, int weeks, 
            SubjectProperty property) {
        super(columnNumber);
        this.semester = semester;
        this.weeks = weeks;
        this.property = property;
    }
    
    @Override
    public void fill(Row row, CurriculumSubject record) {
        Subject subject = record.getSubject();
        double hours = subject.getSemesterHours(semester, record.getCurriculum(),
                property);
        hours /= (double) weeks;
        
        if(hours > 0){
            fill(row, hours);
        } else {
            clear(row);
        }
    }

    public int getSemester() {
        return semester;
    }

    public int getWeeks() {
        return weeks;
    }
}
