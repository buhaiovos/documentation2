package edu.cad.documentelements.k3columns;

import edu.cad.entities.OtherLoadInfo;
import edu.cad.utils.k3.SourceOfFinancing;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class OtherLoadStudentNumberColumn extends AbstractOtherLoadColumn {
    private final SourceOfFinancing source;

    public OtherLoadStudentNumberColumn(int semester, int columnNumber, SourceOfFinancing source) {
        super(semester, columnNumber);
        this.source = source;
    }

    @Override
    public void fill(Row row, OtherLoadInfo info) {
        int totalNumberOfStudents = info.getGroups().stream()
                .mapToInt(source.studentNumberGetter())
                .sum();

        Cell cell = row.getCell(columnNumber);
        cell.setCellValue(totalNumberOfStudents);
    }
}
