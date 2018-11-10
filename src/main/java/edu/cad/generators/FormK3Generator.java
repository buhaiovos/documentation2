package edu.cad.generators;

import edu.cad.daos.HibernateDAO;
import edu.cad.documentelements.k3columns.AbstractK3Column;
import edu.cad.documentelements.k3columns.AllK3ColumnsFactory;
import edu.cad.documentelements.k3columns.StudyLoadColumn;
import edu.cad.entities.Department;
import edu.cad.entities.EducationForm;
import edu.cad.utils.Utils;
import edu.cad.utils.documentutils.K3SemesterStartRowFinder;
import edu.cad.utils.documentutils.RowInserter;
import edu.cad.utils.k3.K3SubjectEntity;
import edu.cad.utils.k3.K3SubjectListCreator;
import edu.cad.utils.k3.SourceOfFinancing;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormK3Generator extends DocumentGenerator {
    private static final String EDUCATION_FORM_TOKEN_BEGINNING = "#edform";
    private static final String FINANCE_SOURCE_TOKEN_BEGINNING = "#source";
    private final Department department;
    private EducationForm educationForm;
    private SourceOfFinancing source;

    FormK3Generator() {
        this.department = new HibernateDAO<>(Department.class).get(1);
    }

    @Override
    void fillInSheet(Sheet sheet) {
        if (currentSheetNeedsToBeFilled(sheet)) {
            fill(sheet);
        }
    }

    private boolean currentSheetNeedsToBeFilled(Sheet sheet) {
        return areEducationFormAndSourceTokenPresent(sheet);
    }

    private boolean areEducationFormAndSourceTokenPresent(Sheet sheet) {
        boolean educationFormIsPresent = false;
        boolean financialSourceIsPresent = false;

        final Row firstRow = sheet.getRow(0);
        for (int cellNumber = 0; cellNumber < sheet.getLastRowNum(); cellNumber++) {
            Cell cell = firstRow.getCell(cellNumber);
            if (cell != null && cell.getStringCellValue() != null) {
                String cellValue = cell.getStringCellValue();
                if (cellValue.contains(EDUCATION_FORM_TOKEN_BEGINNING)) {
                    educationFormIsPresent = true;
                    cell.setCellValue(cellValue);
                    cell.setCellType(CellType.STRING);
                }
                if (cellValue.contains(FINANCE_SOURCE_TOKEN_BEGINNING)) {
                    financialSourceIsPresent = true;
                    cell.setCellValue(cellValue);
                    cell.setCellType(CellType.STRING);
                }

            }
        }

        return educationFormIsPresent && financialSourceIsPresent;
    }

    private void fill(Sheet sheet) {
        setEducationFormAndSource(sheet, EDUCATION_FORM_TOKEN_BEGINNING, FINANCE_SOURCE_TOKEN_BEGINNING);

        List<K3SubjectEntity> firstSemSubjects =
                K3SubjectListCreator.createList(educationForm, source, department, 1);
        Map<Class, List<AbstractK3Column>> columnsMap = getInitializedMap();

        Row firstSemRow = K3SemesterStartRowFinder.findSemesterStartRow(sheet, 1);
        addColumnsToMap(columnsMap, firstSemRow);
        fill(sheet, firstSemSubjects, columnsMap.get(AbstractK3Column.class), firstSemRow.getRowNum());

        List<K3SubjectEntity> secondSemSubjects =
                K3SubjectListCreator.createList(educationForm, source, department, 2);
        Row secondSemRow = K3SemesterStartRowFinder.findSemesterStartRow(sheet, 2);
        fill(sheet, secondSemSubjects, columnsMap.get(AbstractK3Column.class), secondSemRow.getRowNum());
    }

    private Map<Class, List<AbstractK3Column>> getInitializedMap() {
        Map<Class, List<AbstractK3Column>> map = new HashMap<>();
        map.put(AbstractK3Column.class, new ArrayList<>());
        map.put(StudyLoadColumn.class, new ArrayList<>());
        return map;
    }

    private void addColumnsToMap(Map<Class, List<AbstractK3Column>> map, Row row) {
        for (int i = 0; i <= row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            AllK3ColumnsFactory.createAndAddColumn(map, cell, source);
        }
    }

    private void fill(final Sheet sheet,
                      final List<K3SubjectEntity> subjects,
                      final List<AbstractK3Column> columns,
                      int rowNumber) {
        boolean first = true;

        for (K3SubjectEntity subject : subjects) {
            if (!first) {
                RowInserter.insertRow(sheet, rowNumber);
            } else {
                first = false;
            }

            for (AbstractK3Column column : columns) {
                if (column != null) {
                    column.fill(sheet.getRow(rowNumber), subject, "");
                }
            }
            HSSFFormulaEvaluator.evaluateAllFormulaCells(sheet.getWorkbook());
            rowNumber++;
        }
    }

    private void setEducationFormAndSource(Sheet sheet, String educationFormToken, String sourceToken) {
        int id = getId(sheet, 0, 0, educationFormToken);
        educationForm = new HibernateDAO<>(EducationForm.class).get(id);

        id = getId(sheet, 0, 1, sourceToken);
        source = SourceOfFinancing.values()[id];
    }

    private int getId(Sheet sheet, int row, int col, String token) {
        String value;
        Cell cell = sheet.getRow(row).getCell(col);

        if (cell == null || !cell.getCellTypeEnum().equals(CellType.STRING))
            return 0;

        value = sheet.getRow(row).getCell(col).getStringCellValue();
        cell.setCellType(CellType.BLANK);
        if (!value.contains(token))
            return 0;

        value = value.substring(token.length() + 1);
        if (!Utils.isNumber(value))
            return 0;

        return Integer.parseInt(value);
    }

}
