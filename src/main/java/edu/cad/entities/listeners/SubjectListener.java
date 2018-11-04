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
    public void update(Subject subject){
        updateSubSubjects(subject);
        updateGroups(subject);
    }   
    
    private void updateSubSubjects(Subject subject){
        for(CurriculumSubject curriculumSubject : subject.getCurriculumSubjects()){
            Curriculum curriculum = curriculumSubject.getCurriculum();
            subject.setSubSubjects(curriculum, findSubSubjects(subject, curriculum));
        }
    }
    
    private void updateGroups(Subject subject){
        for(CurriculumSubject curriculumSubject : subject.getCurriculumSubjects()){
            Curriculum curriculum = curriculumSubject.getCurriculum();
            if (curriculum instanceof WorkingPlan) {
                subject.getGroups().addAll(((WorkingPlan) curriculum).getGroups());
            }
            
        }
    }
    
    private Set<Subject> findSubSubjects(Subject subject, Curriculum curriculum){
        Set<Subject> subjects = new HashSet<>();
        subjects.add(subject);

        if (curriculum instanceof WorkingPlan)
            return subjects;
        
        for(SubjectDictionary dictionary : subject.getSubject().getSubSubjects()){
            boolean contains = false;
            
            for(Subject element : dictionary.getAcademicSubjects()){  
                if(curriculum.contains(element)){
                    subjects.add(element);
                    if(element.getSubSubjects(curriculum) != null)
                        subjects.addAll(element.getSubSubjects(curriculum));
                    contains = true;
                    break;
                }  
            }
            
            if(!contains){
                Subject appropriate = dictionary.findAppropriate(curriculum);
                
                if(appropriate == null)
                    continue;

                subjects.add(appropriate);
                if(appropriate.getSubSubjects(curriculum) != null)
                    subjects.addAll(appropriate.getSubSubjects(curriculum));
            }
        }
        
        return subjects;
    }
}