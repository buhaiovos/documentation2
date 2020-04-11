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
        Set<SubjectInfo> subjectDetails = new HashSet<>();
        subjectDetails.add(info);

        if (curriculum instanceof WorkingPlan) return subjectDetails;

        for (SubjectHeader dictionary : info.getSubjectHeader().getSubSubjects()) {
            boolean contains = false;

            for (SubjectInfo element : dictionary.getSubjectInfo()) {
                if(curriculum.contains(element)){
                    subjectDetails.add(element);
                    if(element.getSubSubjects(curriculum) != null)
                        subjectDetails.addAll(element.getSubSubjects(curriculum));
                    contains = true;
                    break;
                }  
            }
            
            if(!contains){
                SubjectInfo appropriate = dictionary.findAppropriate(curriculum);

                if (appropriate == null) continue;

                subjectDetails.add(appropriate);
                if(appropriate.getSubSubjects(curriculum) != null)
                    subjectDetails.addAll(appropriate.getSubSubjects(curriculum));
            }
        }

        return subjectDetails;
    }
}
