package edu.cad.generators;

import edu.cad.daos.HibernateDao;
import edu.cad.daos.StudyLoadResultsDao;
import edu.cad.documentelements.k3columns.AbstractK3Column;
import edu.cad.documentelements.k3columns.AllK3ColumnsFactory;
import edu.cad.documentelements.k3columns.StudyLoadColumn;
import edu.cad.entities.Department;
import edu.cad.entities.EducationForm;
import edu.cad.entities.StudyLoadResults;
import edu.cad.entities.SubjectInfo;
import edu.cad.utils.Utils;
import edu.cad.utils.documentutils.FormulaCopier;
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

import java.util.*;
import java.util.stream.Collectors;

public class FormK3Generator extends DocumentGenerator {
    private static final String EDUCATION_FORM_TOKEN_BEGINNING = "#edform";
    private static final String FINANCE_SOURCE_TOKEN_BEGINNING = "#source";
    private static final int FIRST = 1;
    private static final int SECOND = 2;

    private final Department department;
    private final StudyLoadResultsDao studyLoadResultsDao;

    private EducationForm educationForm;
    private SourceOfFinancing sourceOfFinancing;
    private Map<Class, List<AbstractK3Column>> columnClassToListOfColumns;

    FormK3Generator() {
        this.department = new HibernateDao<>(Department.class).get(1);
        this.studyLoadResultsDao = new StudyLoadResultsDao();
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
        initializeColumnsMap();
        setEducationFormAndSource(sheet);
        fillSemester(sheet, FIRST);
        fillSemester(sheet, SECOND);
    }

    private void initializeColumnsMap() {
        this.columnClassToListOfColumns = new HashMap<>();
        columnClassToListOfColumns.put(AbstractK3Column.class, new ArrayList<>());
        columnClassToListOfColumns.put(StudyLoadColumn.class, new ArrayList<>());
    }

    private void setEducationFormAndSource(Sheet sheet) {
        int id = getId(sheet, 0, 0, EDUCATION_FORM_TOKEN_BEGINNING);
        educationForm = new HibernateDao<>(EducationForm.class).get(id);

        id = getId(sheet, 0, 1, FINANCE_SOURCE_TOKEN_BEGINNING);
        sourceOfFinancing = SourceOfFinancing.values()[id];
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

    private void fillSemester(final Sheet sheet, final int semester) {
        Row semesterStartRow = K3SemesterStartRowFinder.findSemesterStartRow(sheet, semester);
        findColumnsForCurrentSemesterPart(semesterStartRow);

        List<K3SubjectEntity> subjects = K3SubjectListCreator.createList(educationForm, sourceOfFinancing, department, semester);

        boolean needToFixFormulas = (semester == SECOND);
        fillRows(semesterStartRow, subjects, needToFixFormulas);

        clearRecordedColumns();
    }

    private void findColumnsForCurrentSemesterPart(Row row) {
        for (int i = 0; i <= row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            AllK3ColumnsFactory.createAndAddColumn(columnClassToListOfColumns, cell, sourceOfFinancing);
        }
    }

    private void clearRecordedColumns() {
        columnClassToListOfColumns.get(AbstractK3Column.class).clear();
        columnClassToListOfColumns.get(StudyLoadColumn.class).clear();
    }

    private void fillRows(final Row firstRowOfSemesterSection,
                          final List<K3SubjectEntity> subjects,
                          final boolean needToFixFormulas) {
        K3SubjectEntity firstSubject = subjects.get(0);
        if (needToFixFormulas) {
            fixShiftedFormulas(firstRowOfSemesterSection, columnClassToListOfColumns.get(StudyLoadColumn.class));
        }
        processAllColumns(firstRowOfSemesterSection, firstSubject);

        Row row = getNextRowForGiven(firstRowOfSemesterSection);
        for (K3SubjectEntity subject : subjects.subList(1, subjects.size())) {
            RowInserter.insertRow(row);
            processAllColumns(row, subject);
            row = getNextRowForGiven(row);
        }
    }

    private void fixShiftedFormulas(Row row, List<AbstractK3Column> abstractK3Columns) {
        abstractK3Columns.forEach(
                column -> FormulaCopier.correctFormula(row.getSheet(), row.getCell(column.getColumnNumber()))
        );
    }

    private void processAllColumns(Row row, K3SubjectEntity subject) {
        fillColumns(row, columnClassToListOfColumns.get(AbstractK3Column.class), subject);
        HSSFFormulaEvaluator.evaluateAllFormulaCells(row.getSheet().getWorkbook());
        readStudyLoadColumnsAndSaveData(
                row,
                columnClassToListOfColumns.get(StudyLoadColumn.class)
                        .stream().map(StudyLoadColumn.class::cast).collect(Collectors.toList()),
                subject
        );
    }

    private void fillColumns(Row row, List<AbstractK3Column> columns, K3SubjectEntity subject) {
        for (AbstractK3Column column : columns) {
            if (column != null) {
                column.fill(row, subject, "");
            }
        }
    }

    private void readStudyLoadColumnsAndSaveData(Row row, List<StudyLoadColumn> columns, K3SubjectEntity subject) {
        StudyLoadResults results = studyLoadResultsDao.findBySubjectInfoAndSourceOfFinanceAndFormOfEducation(
                subject.getSubjectInfo(), this.sourceOfFinancing, this.educationForm
        ).orElseGet(() -> this.newStudyLoadResults(subject.getSubjectInfo()));

        columns.stream()
                .filter(Objects::nonNull)
                .forEach(col -> col.setFormulaResultValueToStudyLoadResultObj(row, results));

        studyLoadResultsDao.update(results);
    }

    private StudyLoadResults newStudyLoadResults(SubjectInfo subjectInfo) {
        var studyLoadResults = new StudyLoadResults();
        studyLoadResults.setSubjectInfo(subjectInfo);
        studyLoadResults.setEducationForm(this.educationForm);
        studyLoadResults.setSourceOfFinancing(this.sourceOfFinancing);
        if (studyLoadResultsDao.create(studyLoadResults)) {
            return studyLoadResults;
        } else {
            throw new RuntimeException("Failed to create new record of study load results");
        }
    }

    private Row getNextRowForGiven(Row firstRowOfSemesterSection) {
        int rowNum = firstRowOfSemesterSection.getRowNum();
        Sheet sheet1 = firstRowOfSemesterSection.getSheet();
        return sheet1.getRow(rowNum + 1);
    }
}
