package edu.cad.entities.listeners;

import edu.cad.entities.*;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import java.util.HashSet;
import java.util.Set;

public class SubjectListener {

    @PostLoad
    @PostPersist
    @PostUpdate
    public void update(SubjectInfo subjectDetails) {
        updateSubSubjects(subjectDetails);
        updateGroups(subjectDetails);
    }

    private void updateSubSubjects(SubjectInfo subjectDetails) {
        for (CurriculumSubject curriculumSubject : subjectDetails.getCurriculumSubjects()) {
            Curriculum curriculum = curriculumSubject.getCurriculum();
            subjectDetails.setSubSubjects(curriculum, findSubSubjects(subjectDetails, curriculum));
        }
    }

    private void updateGroups(SubjectInfo subjectDetails) {
        for (CurriculumSubject curriculumSubject : subjectDetails.getCurriculumSubjects()) {
            Curriculum curriculum = curriculumSubject.getCurriculum();
            if (curriculum instanceof WorkingPlan) {
                subjectDetails.getGroups().addAll(((WorkingPlan) curriculum).getGroups());
            }

        }
    }

    private Set<SubjectInfo> findSubSubjects(SubjectInfo info, Curriculum curriculum) {
        if (curriculum instanceof WorkingPlan) {
            var result = new HashSet<SubjectInfo>();
            result.add(info);
            return result;
        }
        return findForCurriculum(info, curriculum);
    }

    private Set<SubjectInfo> findForCurriculum(SubjectInfo info, Curriculum curriculum) {
        var result = new HashSet<SubjectInfo>();
        result.add(info);
        for (SubjectHeader subSubjectHeader : info.getSubjectHeader().getSubSubjects()) {
            boolean isFound = searchInSubSubjectInfos(curriculum, result, subSubjectHeader);
            if (!isFound) {
                searchAcrossAllWithSameQualification(curriculum, result, subSubjectHeader);
            }
        }
        return result;
    }

    private boolean searchInSubSubjectInfos(Curriculum curriculum,
                                            HashSet<SubjectInfo> result,
                                            SubjectHeader subSubjectHeader) {
        for (SubjectInfo subjectInfo : subSubjectHeader.getSubjectInfo()) {
            if (curriculum.contains(subjectInfo)) {
                result.add(subjectInfo);
                if (subjectInfo.getSubSubjects(curriculum) != null) {
                    result.addAll(subjectInfo.getSubSubjects(curriculum));
                }
                return true;
            }
        }
        return false;
    }

    private void searchAcrossAllWithSameQualification(Curriculum curriculum,
                                                      HashSet<SubjectInfo> result,
                                                      SubjectHeader subSubjectHeader) {
        SubjectInfo appropriate = subSubjectHeader.findAppropriate(curriculum);

        if (appropriate == null) return;

        result.add(appropriate);
        if (appropriate.getSubSubjects(curriculum) != null)
            result.addAll(appropriate.getSubSubjects(curriculum));
    }
}
