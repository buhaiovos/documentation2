package edu.cad.generators;

import edu.cad.daos.HibernateDAO;
import edu.cad.documentelements.areas.*;
import edu.cad.entities.Workplan;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

public class WorkplanGenerator extends CurriculumGenerator {

    private static final String WORKPLAN_ID_MARKER = "#workplan_";
    private List<AbstractDocumentArea> workplanSpecificAreas = new ArrayList<>();

    public WorkplanGenerator(Workbook template) {
        super(template);

    }

    @Override
    public void generate() {
        final Workbook template = getTemplate();
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
        int id = extractCurriculumId(sheet, WORKPLAN_ID_MARKER);
        CurriculumSubjectList subjectList = new CurriculumSubjectList(sheet, 0);
        addWorkplanSpecificAreas(sheet, subjectList);
        Workplan workplan = new HibernateDAO<>(Workplan.class).get(id);

        if (workplan == null)
            return;

        subjectList.fill(workplan);
        fillWorkplanSpecificAreas(workplan);
    }

}
