package edu.cad.documentelements.areas;

import edu.cad.entities.Curriculum;
import edu.cad.entities.SubjectDictionary;
import org.apache.poi.ss.usermodel.Sheet;

public class CurriculumSubjectList extends AbstractSubjectList {
    
    public CurriculumSubjectList(Sheet sheet, int startRow) {
        super(sheet, startRow);
    }
    
    @Override
    public void fill(Curriculum curriculum) {
        super.fill(curriculum, SubjectDictionary::getCurriculumSection);
    }
}
