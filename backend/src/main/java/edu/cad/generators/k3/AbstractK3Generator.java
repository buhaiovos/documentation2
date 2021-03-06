package edu.cad.generators.k3;

import edu.cad.entities.Department;
import edu.cad.entities.EducationForm;
import edu.cad.generators.curriculum.DocumentGenerator;
import edu.cad.study.department.DepartmentService;
import edu.cad.study.educationform.EducationFormService;
import edu.cad.utils.Utils;
import edu.cad.utils.k3.SourceOfFinancing;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import static java.lang.String.format;

@RequiredArgsConstructor
abstract class AbstractK3Generator extends DocumentGenerator {
    private static final String EDUCATION_FORM_TOKEN_BEGINNING = "#edform";
    private static final String FINANCE_SOURCE_TOKEN_BEGINNING = "#source";

    private static final int PAGE_SPECIFIC_TOKEN_CELL_NUM = 0;
    private static final int PAGE_SPECIFIC_TOKEN_ROW_NUM = 0;

    static final int FIRST = 1;
    static final int SECOND = 2;

    private final EducationFormService educationFormService;
    private final DepartmentService departmentService;

    protected boolean isPageSpecificTokenPresent(Sheet sheet, String pageToken) {
        final Cell tokenSpecificCell = getPageSpecificTokenCell(sheet);
        return tokenSpecificCell != null && tokenSpecificCell.getStringCellValue().contains(pageToken);
    }

    protected Cell getPageSpecificTokenCell(Sheet sheet) {
        return sheet.getRow(PAGE_SPECIFIC_TOKEN_ROW_NUM).getCell(PAGE_SPECIFIC_TOKEN_CELL_NUM);
    }

    protected boolean areEducationFormAndFinancialSourceTokensPresent(Sheet sheet) {
        boolean educationFormIsPresent = false;
        boolean financialSourceIsPresent = false;

        final Row firstRow = sheet.getRow(0);

        for (int cellNumber = 0; cellNumber < sheet.getLastRowNum(); cellNumber++) {
            Cell cell = firstRow.getCell(cellNumber);
            if (cell != null && cell.getStringCellValue() != null
                    && cell.getStringCellValue().startsWith("#")) {
                if (isTokenPresent(cell, EDUCATION_FORM_TOKEN_BEGINNING)) {
                    educationFormIsPresent = true;
                } else {
                    financialSourceIsPresent = isTokenPresent(cell, FINANCE_SOURCE_TOKEN_BEGINNING);
                }
            }
        }

        return educationFormIsPresent && financialSourceIsPresent;
    }

    private boolean isTokenPresent(Cell cell, String token) {
        String cellValue = cell.getStringCellValue();
        if (cellValue.contains(token)) {
            cell.setCellValue(cellValue);
            cell.setCellType(CellType.STRING);
            return true;
        }
        return false;
    }

    Department getDepartment(Sheet sheet) {
        // TODO: parse id of department from template and use it to get from dao
        return departmentService.findById(1).orElseThrow();
    }

    SourceOfFinancing getSourceOfFinancing(Sheet sheet) {
        int id;
        id = getId(sheet, 0, 2, FINANCE_SOURCE_TOKEN_BEGINNING);
        return SourceOfFinancing.values()[id];//TODO: this is extremely bad. Rework when some time will be available
    }

    EducationForm getEducationForm(Sheet sheet) {
        int id = getId(sheet, 0, 1, EDUCATION_FORM_TOKEN_BEGINNING);
        return educationFormService.findById(id).orElseThrow();
    }

    private int getId(Sheet sheet, int row, int col, String token) {
        String value;
        Cell cell = sheet.getRow(row).getCell(col);

        if (cell == null || !cell.getCellTypeEnum().equals(CellType.STRING)) {
            throw new IllegalStateException(format("Token '%s' is not found", token));
        }

        value = sheet.getRow(row).getCell(col).getStringCellValue();
        cell.setCellType(CellType.BLANK);
        if (!value.contains(token)) {
            throw new IllegalStateException(format("Token '%s' does not contain required information", value));

        }

        value = value.substring(token.length() + 1);
        if (!Utils.isNumber(value)) {
            throw new IllegalStateException(format("Token '%s' does not contain required information", value));
        }

        return Integer.parseInt(value);
    }
}
