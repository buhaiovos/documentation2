package edu.cad.utils.k3;

import edu.cad.daos.HibernateDAO;
import edu.cad.entities.AcademicGroup;
import edu.cad.entities.CurriculumSubject;
import edu.cad.entities.Department;
import edu.cad.entities.EducationForm;
import edu.cad.entities.Subject;
import edu.cad.entities.SubjectDictionary;
import edu.cad.entities.Workplan;
import edu.cad.utils.hibernateutils.EntityCloner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class K3SubjectListCreator {
    
    public static List<K3SubjectEntity> createList(EducationForm educationForm,
            SourceOfFinancing source, Department department, int semester){
        Set<Subject> subjectSet = createSubjectSet(createWorkplanSet(educationForm), 
                department, semester);
        Map<SubjectDictionary, List<Subject>> subjectMap = new LinkedHashMap<>();
      
        for(Subject subject : subjectSet){
            int position = findPosition(subjectMap, subject);
            Map<Department, List<AcademicGroup>> map = new HashMap<>();
            
            for(AcademicGroup group : subject.getGroups()){
                if(!group.getEducationForm().equals(educationForm)){
                    subject.getGroups().remove(group);
                    continue;
                }       
                
                if(!group.getDepartment().equals(department)){
                    addToMap(map, group);
                    subject.getGroups().remove(group);
                }
            }
            
            subjectMap.get(subject.getSubject()).add(position++, subject);
            addClones(subjectMap, subject, position, map);
        }
        
        List<K3SubjectEntity> entities = new ArrayList<>();
        for(SubjectDictionary dictionary : subjectMap.keySet()){
            entities.addAll(K3SubgroupsCaculator.calculateList(subjectMap.get(dictionary), source));
        }
        
        assignNumbersToSubjectEntities(entities);
        
        return entities;
    }
    
    private static Set<Workplan> createWorkplanSet(EducationForm educationForm){
        List<Workplan> workplans = new HibernateDAO<>(Workplan.class).getAll();
        Iterator<Workplan> iterator = workplans.iterator();
        
        while(iterator.hasNext()){
            Workplan workplan = iterator.next();
            
            if(!workplan.getEducationForm().equals(educationForm)){
                iterator.remove();
            }
        }
        
        return new TreeSet<>(workplans);
    }
    
    private static Set<Subject> createSubjectSet(Set<Workplan> workplans, 
            Department department, int semester){
        int modulo = semester % 2;
        Set<Subject> subjects = new LinkedHashSet<>();
        
        for(Workplan workplan : workplans){
            Set<CurriculumSubject> workplanSubjects = new TreeSet<>();
            workplanSubjects.addAll(workplan.getCurriculumSubjects());
            
            for(CurriculumSubject curriculumSubject : workplanSubjects){
                Subject subject = curriculumSubject.getSubject();
                
                if(subject.getSubject().getDepartment().equals(department)){
                    if(subject.isCourseWork()|| subject.getSemester() % 2 != modulo)
                        continue;
                    
                    subjects.add(subject);
                }
            }
        }
        
        return subjects;
    }
    
    private static void addToMap(Map<Department, List<AcademicGroup>> map, AcademicGroup group){
        if(!map.containsKey(group.getDepartment())){
            map.put(group.getDepartment(), new ArrayList<>());
        }
                    
        map.get(group.getDepartment()).add(group);            
    }
    
    private static void addClones(Map<SubjectDictionary, List<Subject>> subjectMap, 
            Subject subject, int position, Map<Department, List<AcademicGroup>> map){
        
        if(map.isEmpty())
            return;
                
        for(Department key : map.keySet()){
            Subject clone = EntityCloner.clone(Subject.class, subject);
            clone.getGroups().clear();
                    
            for(AcademicGroup group : map.get(key)){
                clone.getGroups().add(group);
            }
                    
            subjectMap.get(subject.getSubject()).add(position++, clone);
        } 
    }
    
    private static int findPosition(Map<SubjectDictionary, List<Subject>> subjectMap, Subject subject){ 
        if(!subjectMap.containsKey(subject.getSubject())){
            subjectMap.put(subject.getSubject(), new ArrayList<>());
            return 0;
        }
        
        int index = 0;        
        for(Subject element: subjectMap.get(subject.getSubject())){
            if(element.getEctsHours() < subject.getEctsHours()){
                index = subjectMap.get(subject.getSubject()).indexOf(element) + 1;
            }
        }
        
        return index;
    }

    private static void assignNumbersToSubjectEntities(List<K3SubjectEntity> entities) {
        int orderNum = 1;
        for (K3SubjectEntity entity : entities) {
            entity.setNumber(orderNum++);
        }
    }
}
