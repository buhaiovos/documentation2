package edu.cad.utils.k3;

import edu.cad.entities.AcademicGroup;
import edu.cad.entities.SubjectInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class K3SubgroupsCaculator {

    public static List<K3SubjectEntity> calculateList(List<SubjectInfo> subjectDetails,
                                                      SourceOfFinancing source) {
        removeEmptySubjects(subjectDetails, source);
        if (subjectDetails.isEmpty())
            return new ArrayList<>();

        List<K3SubjectEntity> entities = createList(subjectDetails);

        for (TypeOfGroupWork type : TypeOfGroupWork.values()) {
            calculateSubgroups(entities, type, source);
        }

        correctPractice(entities);

        return entities;
    }

    private static List<K3SubjectEntity> createList(List<SubjectInfo> subjectDetails) {
        List<K3SubjectEntity> entities = new ArrayList<>();

        for (SubjectInfo info : subjectDetails) {
            entities.add(new K3SubjectEntity(info));
        }

        return entities;
    }

    private static void removeEmptySubjects(List<SubjectInfo> subjectDetails,
                                            SourceOfFinancing source) {
        Iterator<SubjectInfo> iterator = subjectDetails.iterator();

        while (iterator.hasNext()) {
            int total = 0;

            for (AcademicGroup group : iterator.next().getGroups()) {
                total += source.getStudents(group);
            }

            if (total == 0) {
                iterator.remove();
            }
        }
    }

    private static void calculateSubgroups(List<K3SubjectEntity> subjects,
                                           TypeOfGroupWork type, SourceOfFinancing source) {
        if (type.equals(TypeOfGroupWork.Academic)) {
            calculateAcademicGroups(subjects, source);
            return;
        }

        if (type.equals(TypeOfGroupWork.OtherSource)) {
            return;
        }

        K3SubjectEntity currentSubject = subjects.get(0);

        List<Integer> subgroups = new ArrayList<>();
        for (K3SubjectEntity subjectEntity : subjects) {
            SubjectInfo subjectDetails = subjectEntity.getSubjectInfo();

            List<Integer> current = addToPrevSubject(type, subjectDetails, subgroups);

            if (current.isEmpty()) {
                currentSubject.addSubgroups(type, subgroups.size());
                subgroups = createSubgroupsList(type, subjectDetails);
                currentSubject = subjectEntity;
            } else {
                subgroups = current;
                resetHours(type, subjectDetails);
            }
        }

        currentSubject.addSubgroups(type, subgroups.size());
    }

    private static void calculateAcademicGroups(List<K3SubjectEntity> subjects,
                                                SourceOfFinancing source) {
        for (K3SubjectEntity subject : subjects) {
            for (AcademicGroup group : subject.getSubjectInfo().getGroups()) {
                if (source.sourceEquals(group)) {
                    subject.addSubgroups(TypeOfGroupWork.Academic, 1);
                } else {
                    subject.addSubgroups(TypeOfGroupWork.OtherSource, 1);
                }
            }

            if (subjects.indexOf(subject) != 0) {
                resetHours(TypeOfGroupWork.Academic, subject.getSubjectInfo());
            }
        }
    }

    private static List<Integer> addToPrevSubject(TypeOfGroupWork type, SubjectInfo subjectDetails,
                                                  List<Integer> subgroups) {
        List<Integer> result = new ArrayList<>();
        result.addAll(subgroups);

        for (AcademicGroup group : subjectDetails.getGroups()) {
            if (!addToList(type, group, result)) {
                return new ArrayList<>();
            }
        }

        return result;
    }

    private static List<Integer> createSubgroupsList(TypeOfGroupWork type, SubjectInfo subjectDetails) {
        List<Integer> result = new ArrayList<>();

        for (AcademicGroup group : subjectDetails.getGroups()) {
            if (!addToList(type, group, result)) {
                int total = calculateSubgroups(type, group.getTotalStudents());

                for (int i = 0; i < total - 1; i++) {
                    result.add(type.getMaxStudents());
                }

                result.add(group.getTotalStudents() % type.getMaxStudents());
            }
        }

        return result;
    }

    private static boolean addToList(TypeOfGroupWork type, AcademicGroup group,
                                     List<Integer> subgroups) {
        for (int i = 0; i < subgroups.size(); i++) {
            if (canBeAdded(type, group, subgroups.get(i))) {
                subgroups.set(i, subgroups.get(i) + group.getTotalStudents());
                return true;
            }
        }

        return false;
    }

    private static boolean canBeAdded(TypeOfGroupWork type, AcademicGroup group,
                                      int total) {
        int min = type.getMinStudents();

        if (total > min && group.getTotalStudents() > min)
            return false;

        return group.getTotalStudents() + total <= type.getMaxStudents();
    }

    private static void resetHours(TypeOfGroupWork type, SubjectInfo subjectDetails) {
        switch (type) {
            case Academic:
                subjectDetails.setLectures(0);
                break;
            case Practice:
                subjectDetails.setPractices(0);
                break;
            case Lab:
                subjectDetails.setLabs(0);
                break;
        }
    }

    private static int calculateSubgroups(TypeOfGroupWork type, int total) {
        return (int) Math.ceil(total / (double) type.getMaxStudents());
    }

    private static void correctPractice(List<K3SubjectEntity> subjects) {
        TypeOfGroupWork practice = TypeOfGroupWork.Practice;
        K3SubjectEntity firstSubject = subjects.get(0);

        for (K3SubjectEntity subject : subjects) {
            if (subject.getSubgroup(TypeOfGroupWork.Lab) == 0) {
                firstSubject.addSubgroups(practice, subject.getSubgroup(practice));
                subject.resetSubgroups(practice);
            }
        }
    }
}
