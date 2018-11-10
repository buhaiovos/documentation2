package edu.cad.utils.k3;

import edu.cad.daos.HibernateDAO;
import edu.cad.entities.*;
import edu.cad.utils.hibernateutils.EntityCloner;

import java.util.*;

public class K3SubjectListCreator {

    public static List<K3SubjectEntity> createList(final EducationForm educationForm,
                                                   final SourceOfFinancing source,
                                                   final Department department,
                                                   final int semester) {
        Set<WorkingPlan> includedWorkingPlans = createWorkPlanSet(educationForm);
        Set<Subject> subjectSet = createSubjectSet(includedWorkingPlans, department, semester);

        Map<SubjectDictionary, List<Subject>> subjectMap = new LinkedHashMap<>();

        for (Subject subject : subjectSet) {
            int position = findPosition(subjectMap, subject);
            Map<Department, List<AcademicGroup>> map = new HashMap<>();

            for (AcademicGroup group : subject.getGroups()) {
                if (!group.getEducationForm().equals(educationForm)) {
                    subject.getGroups().remove(group);
                    continue;
                }

                if (!group.getDepartment().equals(department)) {
                    addToMap(map, group);
                    subject.getGroups().remove(group);
                }
            }

            subjectMap.get(subject.getSubject()).add(position++, subject);
            addClones(subjectMap, subject, position, map);
        }

        List<K3SubjectEntity> entities = new ArrayList<>();
        for (SubjectDictionary dictionary : subjectMap.keySet()) {
            entities.addAll(K3SubgroupsCaculator.calculateList(subjectMap.get(dictionary), source));
        }

        assignNumbersToSubjectEntities(entities);

        return entities;
    }

    private static Set<WorkingPlan> createWorkPlanSet(EducationForm educationForm) {
        List<WorkingPlan> workingPlans = new HibernateDAO<>(WorkingPlan.class).getAll();

        workingPlans.removeIf(
                workPlan -> !workPlan.getEducationForm().equals(educationForm)
        );

        return new TreeSet<>(workingPlans);
    }

    private static Set<Subject> createSubjectSet(Set<WorkingPlan> workingPlans, Department department, int semester) {
        int modulo = semester % 2;
        Set<Subject> subjects = new LinkedHashSet<>();

        for (WorkingPlan workingPlan : workingPlans) {
            Set<CurriculumSubject> workingPlanSubjects = new TreeSet<>();
            workingPlanSubjects.addAll(workingPlan.getCurriculumSubjects());

            for (CurriculumSubject curriculumSubject : workingPlanSubjects) {
                Subject subject = curriculumSubject.getSubject();

                if (subject.getSubject().getDepartment().equals(department)) {
                    if (subject.isCourseWork() || subject.getSemester() % 2 != modulo)
                        continue;

                    subjects.add(subject);
                }
            }
        }

        return subjects;
    }

    private static void addToMap(Map<Department, List<AcademicGroup>> map, AcademicGroup group) {
        if (!map.containsKey(group.getDepartment())) {
            map.put(group.getDepartment(), new ArrayList<>());
        }

        map.get(group.getDepartment()).add(group);
    }

    private static void addClones(Map<SubjectDictionary, List<Subject>> subjectMap,
                                  Subject subject,
                                  int position,
                                  Map<Department, List<AcademicGroup>> map) {
        if (map.isEmpty())
            return;

        for (Department key : map.keySet()) {
            Subject clone = EntityCloner.clone(Subject.class, subject);
            clone.getGroups().clear();

            for (AcademicGroup group : map.get(key)) {
                clone.getGroups().add(group);
            }

            subjectMap.get(subject.getSubject()).add(position++, clone);
        }
    }

    private static int findPosition(Map<SubjectDictionary, List<Subject>> subjectMap, Subject subject) {
        if (!subjectMap.containsKey(subject.getSubject())) {
            subjectMap.put(subject.getSubject(), new ArrayList<>());
            return 0;
        }

        int index = 0;
        for (Subject element : subjectMap.get(subject.getSubject())) {
            if (element.getEctsHours() < subject.getEctsHours()) {
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
