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
        Set<SubjectInfo> subjectDetailsSet = createSubjectSet(includedWorkingPlans, department, semester);

        Map<SubjectHeader, List<SubjectInfo>> subjectMap = new LinkedHashMap<>();

        for (SubjectInfo subjectDetails : subjectDetailsSet) {
            int position = findPosition(subjectMap, subjectDetails);
            Map<Department, List<AcademicGroup>> map = new HashMap<>();

            for (AcademicGroup group : subjectDetails.getGroups()) {
                if (!group.getEducationForm().equals(educationForm)) {
                    subjectDetails.getGroups().remove(group);
                    continue;
                }

                if (!group.getDepartment().equals(department)) {
                    addToMap(map, group);
                    subjectDetails.getGroups().remove(group);
                }
            }

            subjectMap.get(subjectDetails.getSubjectHeader()).add(position++, subjectDetails);
            addClones(subjectMap, subjectDetails, position, map);
        }

        List<K3SubjectEntity> entities = new ArrayList<>();
        for (SubjectHeader dictionary : subjectMap.keySet()) {
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

    private static Set<SubjectInfo> createSubjectSet(Set<WorkingPlan> workingPlans, Department department, int semester) {
        int modulo = semester % 2;
        Set<SubjectInfo> subjectDetails = new LinkedHashSet<>();

        for (WorkingPlan workingPlan : workingPlans) {
            Set<CurriculumSubject> workingPlanSubjects = new TreeSet<>();
            workingPlanSubjects.addAll(workingPlan.getCurriculumSubjects());

            for (CurriculumSubject curriculumSubject : workingPlanSubjects) {
                SubjectInfo info = curriculumSubject.getSubjectInfo();

                if (info.getSubjectHeader().getDepartment().equals(department)) {
                    if (info.isCourseWork() || info.getSemester() % 2 != modulo)
                        continue;

                    subjectDetails.add(info);
                }
            }
        }

        return subjectDetails;
    }

    private static void addToMap(Map<Department, List<AcademicGroup>> map, AcademicGroup group) {
        if (!map.containsKey(group.getDepartment())) {
            map.put(group.getDepartment(), new ArrayList<>());
        }

        map.get(group.getDepartment()).add(group);
    }

    private static void addClones(Map<SubjectHeader, List<SubjectInfo>> subjectMap,
                                  SubjectInfo subjectDetails,
                                  int position,
                                  Map<Department, List<AcademicGroup>> departmentToGroups) {
        if (departmentToGroups.isEmpty())
            return;

        for (Department department : departmentToGroups.keySet()) {
            SubjectInfo clone = EntityCloner.clone(SubjectInfo.class, subjectDetails);
            clone.getGroups().clear();

            for (AcademicGroup group : departmentToGroups.get(department)) {
                clone.getGroups().add(group);
            }

            subjectMap.get(subjectDetails.getSubjectHeader()).add(position++, clone);
        }
    }

    private static int findPosition(Map<SubjectHeader, List<SubjectInfo>> subjectMap, SubjectInfo subjectDetails) {
        if (!subjectMap.containsKey(subjectDetails.getSubjectHeader())) {
            subjectMap.put(subjectDetails.getSubjectHeader(), new ArrayList<>());
            return 0;
        }

        int index = 0;
        for (SubjectInfo element : subjectMap.get(subjectDetails.getSubjectHeader())) {
            if (element.getEctsHours() < subjectDetails.getEctsHours()) {
                index = subjectMap.get(subjectDetails.getSubjectHeader()).indexOf(element) + 1;
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
