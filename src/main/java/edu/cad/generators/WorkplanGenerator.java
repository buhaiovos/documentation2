package edu.cad.generators;

import edu.cad.daos.HibernateDAO;
import edu.cad.documentelements.areas.AbstractDocumentArea;
import edu.cad.documentelements.areas.CurriculumSubjectList;
import edu.cad.documentelements.areas.DiplomaPreparationArea;
import edu.cad.documentelements.areas.PracticeArea;
import edu.cad.documentelements.areas.StateCertificationArea;
import edu.cad.entities.Workplan;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class WorkplanGenerator extends CurriculumGenerator {
    
    private List<AbstractDocumentArea> workplanSpecificAreas = new ArrayList<>();
    
    public WorkplanGenerator(Workbook template) {
        super(template);
    }
    
    @Override
    public void generate() throws IOException {
        for (int i = 0; i < template.getNumberOfSheets(); i++) {
            generateSheet(template.getSheetAt(i));
        }
    }

    private void addWorkplanSpecificAreas(Sheet sheet, 
            CurriculumSubjectList subjectList) {
        
        int startPos = subjectList.getRowNumer();
        workplanSpecificAreas.clear();
        workplanSpecificAreas.add(new PracticeArea(sheet, startPos));
        workplanSpecificAreas.add(new StateCertificationArea(sheet, startPos));
        workplanSpecificAreas.add(new DiplomaPreparationArea(sheet, startPos));
        
    }

    private void fillWorkplanSpecificAreas(Workplan workplan) {
        for (AbstractDocumentArea area : workplanSpecificAreas) {
            area.fill(workplan);
        }
    }
    
    private void generateSheet(Sheet sheet) {
        int id = getCurriculumId(sheet, "#workplan");
        CurriculumSubjectList subjectList = new CurriculumSubjectList(sheet, 0);
        addWorkplanSpecificAreas(sheet, subjectList);
        Workplan workplan = new HibernateDAO<>(Workplan.class).get(id);
        
        if (workplan == null)
            return;
        
        subjectList.fill(workplan);
        fillWorkplanSpecificAreas(workplan);
    }
    
}
