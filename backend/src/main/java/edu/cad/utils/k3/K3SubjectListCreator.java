package edu.cad.utils.k3;

import edu.cad.daos.HibernateDao;
import edu.cad.entities.*;
import edu.cad.utils.hibernateutils.EntityCloner;

import java.util.*;

public class K3SubjectListCreator {

    public static List<K3SubjectEntity> createList(final EducationForm educationForm,
                                                   final SourceOfFinancing source,
                                                   final Department department,
                                                   final int semester) {
        Set<WorkingPlan> includedWorkingPlans = createWorkPlanSet(educationForm);
        Set<SubjectInfo> allSubjectsInfo = createSubjectSet(includedWorkingPlans, department, semester);

        Map<SubjectHeader, List<SubjectInfo>> subjectHeaderToSubjectInfo = new LinkedHashMap<>();

        for (SubjectInfo subjectInfo : allSubjectsInfo) {
            int position = findPosition(subjectHeaderToSubjectInfo, subjectInfo);
            Map<Department, List<AcademicGroup>> map = new HashMap<>();

            for (AcademicGroup group : subjectInfo.getGroups()) {
                if (!group.getEducationForm().equals(educationForm)) {
                    subjectInfo.getGroups().remove(group);
                    continue;
                }

                if (!group.getDepartment().equals(department)) {
                    addToMap(map, group);
                    subjectInfo.getGroups().remove(group);
                }
            }

            subjectHeaderToSubjectInfo.get(subjectInfo.getSubjectHeader()).add(position++, subjectInfo);
            addClones(subjectHeaderToSubjectInfo, subjectInfo, position, map);
        }

        List<K3SubjectEntity> subjects = new ArrayList<>();
        for (SubjectHeader subjectHeader : subjectHeaderToSubjectInfo.keySet()) {
            subjects.addAll(K3SubgroupsCalculator.calculateList(subjectHeaderToSubjectInfo.get(subjectHeader), source));
        }

        assignNumbersToSubjectEntities(subjects);

        return subjects;
    }

    private static Set<WorkingPlan> createWorkPlanSet(EducationForm educationForm) {
        List<WorkingPlan> workingPlans = new HibernateDao<>(WorkingPlan.class).getAll();

        workingPlans.removeIf(
                workPlan -> !workPlan.getEducationForm().equals(educationForm)
        );

        return new TreeSet<>(workingPlans);
    }

    private static Set<SubjectInfo> createSubjectSet(Set<WorkingPlan> workingPlans, Department department, int semester) {
        int modulo = semester % 2;
        Set<SubjectInfo> subjectDetails = new LinkedHashSet<>();

        for (WorkingPlan workingPlan : workingPlans) {
            Set<CurriculumSubject> workingPlanSubjects = new TreeSet<>(workingPlan.getCurriculumSubjects());

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

    private static void addToMap(Map<Department, List<AcademicGroup>> departmentToGroups, AcademicGroup group) {
        List<AcademicGroup> groups = departmentToGroups.putIfAbsent(group.getDepartment(), new ArrayList<>(List.of(group)));
        if (groups != null) { // null when first insertion occurs, hence no need to insert
            groups.add(group);
        }
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

    private static int findPosition(Map<SubjectHeader, List<SubjectInfo>> subjectHeaderToSubjectInfos, SubjectInfo subjectInfo) {
        int index = 0;
        SubjectHeader subjectHeader = subjectInfo.getSubjectHeader();
        List<SubjectInfo> subjectInfos;
        if (null != (subjectInfos = subjectHeaderToSubjectInfos.putIfAbsent(subjectHeader, new ArrayList<>()))) {
            for (SubjectInfo info : subjectInfos) {
                if (info.getEctsHours() < subjectInfo.getEctsHours()) {
                    index = subjectInfos.indexOf(info) + 1;
                }
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
