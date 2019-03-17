package edu.cad.utils;

import edu.cad.entities.AcademicGroup;
import edu.cad.entities.SubjectInfo;

import java.util.Set;

public final class SubjectNameUtil {
    private SubjectNameUtil(){
    }

    public static String getDetailedSubjectName(SubjectInfo subjectInfo, String faculty) {
        String mainString = getMainString(subjectInfo, faculty);
        String groupsString = getGroupsString(subjectInfo.getGroups());

        return mainString + groupsString;
    }

    private static String getMainString(SubjectInfo subjectInfo, String faculty) {
        final String DELIMITER = " - ";
        StringBuilder value = new StringBuilder();

        value.append(faculty);
        value.append(DELIMITER);
        value.append(subjectInfo.getSubjectHeader().getDenotation());
        value.append(DELIMITER);
        value.append(subjectInfo.getEctsHours());
        value.append(DELIMITER);

        return value.toString();
    }

    private static String getGroupsString(Set<AcademicGroup> groups) {
        StringBuilder groupStringBuilder = new StringBuilder();

        for (AcademicGroup group : groups) {
            groupStringBuilder.append(group.getCipher());
            groupStringBuilder.append("(");
            groupStringBuilder.append(group.getBudgetaryStudents());
            groupStringBuilder.append("+");
            groupStringBuilder.append(group.getContractStudents());
            groupStringBuilder.append("),");
        }

        groupStringBuilder.deleteCharAt(groupStringBuilder.length() - 1);

        return groupStringBuilder.toString();
    }
}
