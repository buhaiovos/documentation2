package edu.cad.controllers.dto;

import edu.cad.entities.SubjectStudyLoad;
import edu.cad.utils.SubjectNameUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static edu.cad.domain.StudyLoadType.*;

@Service
public class SubjectStudyLoadTransformer {

    public SubjectLoadDto toDto(SubjectStudyLoad studyLoad) {
        var subjectLoadDto = new SubjectLoadDto();
        subjectLoadDto.setId(studyLoad.getId());
        subjectLoadDto.setName(SubjectNameUtil.getDetailedSubjectName(studyLoad.getSubjectInfo(), "ІПСА"));
        subjectLoadDto.setLoadAmountByType(collectLoadDataToMap(studyLoad));
        return subjectLoadDto;
    }

    private Map<String, Double> collectLoadDataToMap(SubjectStudyLoad studyLoad) {
        var loadData = new HashMap<String, Double>();
        loadData.put(LECTURES.getId(), studyLoad.getLectures());
        loadData.put(PRACTICES.getId(), studyLoad.getPractices());
        loadData.put(LABS.getId(), studyLoad.getLabs());
        loadData.put(INDIVIDUALS.getId(), studyLoad.getIndividuals());
        loadData.put(EXAMS.getId(), studyLoad.getExams());
        loadData.put(CREDITS.getId(), studyLoad.getCredits());
        loadData.put(CONTROL_WORKS.getId(), studyLoad.getControlWorks());
        loadData.put(COURSE_PROJECTS.getId(), studyLoad.getCourseProjects());
        loadData.put(COURSE_WORKS.getId(), studyLoad.getCourseWorks());
        loadData.put(RGRS.getId(), studyLoad.getRGRs());
        loadData.put(DKR.getId(), studyLoad.getDKRs());
        loadData.put(REFERATS.getId(), studyLoad.getReferats());
        loadData.put(CONSULTATIONS.getId(), studyLoad.getConsultations());

        return loadData;
    }
}
