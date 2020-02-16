package edu.cad.generators;

import edu.cad.documentelements.areas.*;
import edu.cad.entities.WorkingPlan;
import edu.cad.study.workingplan.WorkingPlanService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
class WorkingPlanGenerator extends DocumentGenerator {
    private static final String WORK_PLAN_ID_MARKER = "#workplan_";

    private final WorkingPlanService workingPlanService;

    @Override
    public void fillInSheet(Sheet sheet) {
        int id = GenerationUtils.extractCurriculumId(sheet, WORK_PLAN_ID_MARKER);
        WorkingPlanSubjectList subjectList = new WorkingPlanSubjectList(sheet, 0);
        WorkingPlan workplan = workingPlanService.findById(id).orElseThrow();

        if (workplan == null) {
            return;
        }
        subjectList.fill(workplan);
        fillWorkPlanSpecificAreas(workplan, getWorkPlanSpecificAreas(sheet, subjectList));
    }

    private List<AbstractDocumentArea> getWorkPlanSpecificAreas(Sheet sheet, WorkingPlanSubjectList subjectList) {
        int startPos = subjectList.getRowNumer();
        var workPlanSpecificAreas = new ArrayList<AbstractDocumentArea>();
        workPlanSpecificAreas.add(new PracticeArea(sheet, startPos));
        workPlanSpecificAreas.add(new StateCertificationArea(sheet, startPos));
        workPlanSpecificAreas.add(new DiplomaPreparationArea(sheet, startPos));
        return workPlanSpecificAreas;
    }

    private void fillWorkPlanSpecificAreas(WorkingPlan workplan, List<AbstractDocumentArea> areas) {
        for (AbstractDocumentArea area : areas) {
            area.fill(workplan);
        }
    }

}
