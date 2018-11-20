package edu.cad.generators.k3;

import edu.cad.documentelements.columns.AbstractColumn;
import edu.cad.documentelements.k3columns.OtherLoadColumnsFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.Map;

public class OtherActivitiesPageGenerator extends FormK3Generator {
    private static final String K3_OTHER_PAGE_COLUMN_TOKEN_BEGINNING = "#k3(OC)";
    private static final String PAGE_TOKEN = "#other";

    @Override
    public boolean canGenerate(Sheet sheet) {
        return isPageSpecificTokenPresent(sheet, PAGE_TOKEN) && areEducationFormAndFinancialSourceTokensPresent(sheet);
    }

    @Override
    public void fillInSheet(Sheet sheet) {
        // collect columns
        Map<Integer, List<AbstractColumn>> semesterToColumns = findColumns(sheet);

        // construct all areas
        throw new UnsupportedOperationException("To be implemented...");
    }

    private Map<Integer, List<AbstractColumn>> findColumns(Sheet sheet) {
        // traverse sheet to find columns
        Row row = sheet.getRow(1);
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell currentCell = row.getCell(i);
            if (currentCell != null && currentCell.getStringCellValue() != null) {
                String stringCellValue = currentCell.getStringCellValue();
                if (stringCellValue.contains(K3_OTHER_PAGE_COLUMN_TOKEN_BEGINNING)) {
                    OtherLoadColumnsFactory.getColumn(stringCellValue);
                }
            }
        }
    }
}
