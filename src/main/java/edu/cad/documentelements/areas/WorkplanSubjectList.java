package edu.cad.documentelements.areas;

import edu.cad.entities.Curriculum;
import edu.cad.entities.SubjectDictionary;
import org.apache.poi.ss.usermodel.Sheet;

public class WorkplanSubjectList extends AbstractSubjectList{

    public WorkplanSubjectList(Sheet sheet, int startRow) {
        super(sheet, startRow);
    }
    
    @Override
    public void fill(Curriculum curriculum) {
        super.fill(curriculum, SubjectDictionary::getWorkplanSection);
    }
}
