package edu.cad.study.load.subject;

import edu.cad.domain.StudyLoadType;
import edu.cad.entities.SubjectStudyLoad;
import edu.cad.utils.SubjectNameUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static edu.cad.domain.StudyLoadType.*;

@Service
@RequiredArgsConstructor
public class SubjectStudyLoadTransformer {
    private final SubjectLoadSubtractionService subjectLoadSubtractionService;

    public SubjectLoadDto toDto(SubjectStudyLoad studyLoad) {
        var subjectLoadDto = new SubjectLoadDto();
        subjectLoadDto.setId(studyLoad.getId());
        subjectLoadDto.setName(SubjectNameUtil.getDetailedSubjectName(studyLoad.getSubjectInfo(), "ІПСА"));
        subjectLoadDto.setLoadAmountByType(collectLoadDataToMap(studyLoad));
        return subjectLoadSubtractionService.subtractDistributedLoad(subjectLoadDto);
    }

    private Map<StudyLoadType, Double> collectLoadDataToMap(SubjectStudyLoad studyLoad) {
        var loadData = new HashMap<StudyLoadType, Double>();
        loadData.put(LECTURES, studyLoad.getLectures());
        loadData.put(PRACTICES, studyLoad.getPractices());
        loadData.put(LABS, studyLoad.getLabs());
        loadData.put(INDIVIDUALS, studyLoad.getIndividuals());
        loadData.put(EXAMS, studyLoad.getExams());
        loadData.put(CREDITS, studyLoad.getCredits());
        loadData.put(CONTROL_WORKS, studyLoad.getControlWorks());
        loadData.put(COURSE_PROJECTS, studyLoad.getCourseProjects());
        loadData.put(COURSE_WORKS, studyLoad.getCourseWorks());
        loadData.put(RGRS, studyLoad.getRGRs());
        loadData.put(DKR, studyLoad.getDKRs());
        loadData.put(REFERATS, studyLoad.getReferats());
        loadData.put(CONSULTATIONS, studyLoad.getConsultations());

        return loadData;
    }
}
