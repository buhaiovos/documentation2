package edu.cad.documentelements.k3columns;

import edu.cad.utils.SubjectNameUtil;
import edu.cad.utils.k3.K3SubjectEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

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
        return SubjectNameUtil.getDetailedSubjectName(subject.getSubjectInfo(), faculty);
    }
}
