package edu.cad.documentelements.areas;

import edu.cad.entities.Curriculum;
import edu.cad.entities.SubjectHeader;
import org.apache.poi.ss.usermodel.Sheet;

public class WorkingPlanSubjectList extends AbstractSubjectList {

    public WorkingPlanSubjectList(Sheet sheet, int startRow) {
        super(sheet, startRow);
    }

    @Override
    public void fill(Curriculum curriculum) {
        super.fill(curriculum, SubjectHeader::getWorkingPlanSection);
    }
}
