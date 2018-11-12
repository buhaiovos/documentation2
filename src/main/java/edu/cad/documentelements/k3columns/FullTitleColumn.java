package edu.cad.documentelements.k3columns;

import edu.cad.entities.AcademicGroup;
import edu.cad.entities.SubjectInfo;
import edu.cad.utils.k3.K3SubjectEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.util.Set;

public class FullTitleColumn extends AbstractK3Column {
    private final String faculty;

    public FullTitleColumn(int columnNumber, String faculty) {
        super(columnNumber);
        this.faculty = faculty;
    }

    @Override
    public void fill(Row row, K3SubjectEntity subject, String value) {
        row.setHeight((short) -1);
        setCellStyle(row);
        value = getValue(subject);
        fill(row, value);
    }

    private void setCellStyle(Row row) {
        Cell cell = row.getCell(columnNumber);
        CellStyle style = cell.getCellStyle();
        style.setWrapText(true);
        cell.setCellStyle(style);
    }

    @Override
    public String getValue(K3SubjectEntity subject) {
        String mainString = getMainString(subject.getSubjectInfo());
        String groupsString = getGroupsString(subject.getSubjectInfo().getGroups());

        return mainString + groupsString;
    }

    private String getMainString(SubjectInfo subjectDetails) {
        final String DELIMITER = " - ";
        StringBuilder value = new StringBuilder();

        value.append(faculty);
        value.append(DELIMITER);
        value.append(subjectDetails.getSubjectHeader().getDenotation());
        value.append(DELIMITER);
        value.append(subjectDetails.getEctsHours());
        value.append(DELIMITER);

        return value.toString();
    }

    private String getGroupsString(Set<AcademicGroup> groups) {
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
