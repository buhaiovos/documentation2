package edu.cad.documentelements.k3columns;

import edu.cad.entities.AcademicGroup;
import edu.cad.entities.Subject;
import edu.cad.utils.k3.K3SubjectEntity;
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
    public String getValue(K3SubjectEntity subject) {
        String mainString = getMainString(subject.getSubject());
        String groupsString = getGroupsString(subject.getSubject().getGroups());

        return mainString + groupsString;
        //row.getCell(columnNumber).setCellValue(mainString + groupsString);
    }

    @Override
    public void fill(Row row, K3SubjectEntity subject, String value) {
        CellStyle style = row.getSheet().getWorkbook().createCellStyle();
        style.setWrapText(true);
        row.getCell(columnNumber).setCellStyle(style);
//        row.setHeight((short) -1);
        value = getValue(subject);
        fill(row, value);
    }

    private String getMainString(Subject subject) {
        final String DELIMITER = " - ";
        StringBuilder value = new StringBuilder();

        value.append(faculty);
        value.append(DELIMITER);
        value.append(subject.getSubject().getDenotation());
        value.append(DELIMITER);
        value.append(subject.getEctsHours());
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
