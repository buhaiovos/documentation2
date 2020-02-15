package edu.cad.generators;

import edu.cad.documentelements.areas.*;
import edu.cad.entities.WorkingPlan;
import edu.cad.study.workingplan.WorkingPlanService;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

class WorkingPlanGenerator extends CurriculumGenerator {
    private static final String WORK_PLAN_ID_MARKER = "#workplan_";

    private List<AbstractDocumentArea> workPlanSpecificAreas = new ArrayList<>();
    private WorkingPlanService workingPlanService;

    @Override
    public void fillInSheet(Sheet sheet) {
        int id = extractCurriculumId(sheet, WORK_PLAN_ID_MARKER);
        WorkingPlanSubjectList subjectList = new WorkingPlanSubjectList(sheet, 0);
        WorkingPlan workplan = workingPlanService.findById(id).orElseThrow();

        if (workplan == null) {
            return;
        }
        subjectList.fill(workplan);

        addWorkPlanSpecificAreas(sheet, subjectList);
        fillWorkPlanSpecificAreas(workplan);
    }

    private void addWorkPlanSpecificAreas(Sheet sheet, WorkingPlanSubjectList subjectList) {
        int startPos = subjectList.getRowNumer();
        workPlanSpecificAreas.clear();
        workPlanSpecificAreas.add(new PracticeArea(sheet, startPos));
        workPlanSpecificAreas.add(new StateCertificationArea(sheet, startPos));
        workPlanSpecificAreas.add(new DiplomaPreparationArea(sheet, startPos));

    }

    private void fillWorkPlanSpecificAreas(WorkingPlan workplan) {
        for (AbstractDocumentArea area : workPlanSpecificAreas) {
            area.fill(workplan);
        }
    }

}
