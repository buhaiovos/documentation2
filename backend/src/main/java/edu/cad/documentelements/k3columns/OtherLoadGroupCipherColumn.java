package edu.cad.documentelements.k3columns;

import edu.cad.entities.AcademicGroup;
import edu.cad.entities.OtherLoadInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.stream.Collectors;

public class OtherLoadGroupCipherColumn extends AbstractOtherLoadColumn {
    public OtherLoadGroupCipherColumn(int semester, int columnNumber) {
        super(semester, columnNumber);
    }

    @Override
    public void fill(Row row, OtherLoadInfo info) {
        String concatenatedGroupCiphers = info.getGroups().stream()
                .map(AcademicGroup::getCipher)
                .collect(Collectors.joining(", "));

        Cell cell = row.getCell(columnNumber);
        cell.setCellValue(concatenatedGroupCiphers);
    }
}
