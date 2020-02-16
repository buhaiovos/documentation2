package edu.cad.generators.k3;

import edu.cad.documentelements.k3columns.AbstractK3Column;
import edu.cad.documentelements.k3columns.K3SubjectPageColumnsFactory;
import edu.cad.documentelements.k3columns.SubjectStudyLoadColumn;
import edu.cad.entities.Department;
import edu.cad.entities.EducationForm;
import edu.cad.entities.SubjectInfo;
import edu.cad.entities.SubjectStudyLoad;
import edu.cad.study.department.DepartmentService;
import edu.cad.study.educationform.EducationFormService;
import edu.cad.study.load.subject.SubjectStudyLoadRepositoryWrapper;
import edu.cad.utils.documentutils.FormulaCopier;
import edu.cad.utils.documentutils.K3SemesterStartRowFinder;
import edu.cad.utils.documentutils.RowInserter;
import edu.cad.utils.k3.K3SubjectEntity;
import edu.cad.utils.k3.K3SubjectListCreator;
import edu.cad.utils.k3.SourceOfFinancing;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
class SubjectListPageGenerator extends AbstractK3Generator {
    private static final String PAGE_TOKEN = "#subj";

    private final SubjectStudyLoadRepositoryWrapper studyLoadResultsDao;

    private Map<Class<? extends AbstractK3Column>, List<AbstractK3Column>> columnClassToListOfColumns;
    private Department department;
    private EducationForm educationForm;
    private SourceOfFinancing sourceOfFinancing;

    public SubjectListPageGenerator(EducationFormService educationFormService,
                                    DepartmentService departmentService,
                                    SubjectStudyLoadRepositoryWrapper studyLoadResultsDao) {
        super(educationFormService, departmentService);
        this.studyLoadResultsDao = studyLoadResultsDao;
    }

    @Override
    public boolean canGenerate(Sheet sheet) {
        return isPageSpecificTokenPresent(sheet, PAGE_TOKEN) && areEducationFormAndFinancialSourceTokensPresent(sheet);
    }

    @Override
    public void fillInSheet(Sheet sheet) {
        init(sheet);
        fillSemester(sheet, FIRST);
        fillSemester(sheet, SECOND);
    }

    private void init(Sheet sheet) {
        initializeColumnsMap();
        this.department = getDepartment(sheet);
        this.sourceOfFinancing = getSourceOfFinancing(sheet);
        this.educationForm = getEducationForm(sheet);
    }

    private void initializeColumnsMap() {
        this.columnClassToListOfColumns = new HashMap<>();
        columnClassToListOfColumns.put(AbstractK3Column.class, new ArrayList<>());
        columnClassToListOfColumns.put(SubjectStudyLoadColumn.class, new ArrayList<>());
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
            K3SubjectPageColumnsFactory.createAndAddColumn(columnClassToListOfColumns, cell, sourceOfFinancing);
        }
    }

    private void clearRecordedColumns() {
        columnClassToListOfColumns.get(AbstractK3Column.class).clear();
        columnClassToListOfColumns.get(SubjectStudyLoadColumn.class).clear();
    }

    private void fillRows(final Row firstRowOfSemesterSection, final List<K3SubjectEntity> subjects,
                          final boolean needToFixFormulas) {
        K3SubjectEntity firstSubject = subjects.get(0);
        if (needToFixFormulas) {
            fixShiftedFormulas(firstRowOfSemesterSection, columnClassToListOfColumns.get(SubjectStudyLoadColumn.class));
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
        readLoadColumnsAndSaveData(
                row,
                columnClassToListOfColumns.get(SubjectStudyLoadColumn.class)
                        .stream().map(SubjectStudyLoadColumn.class::cast).collect(Collectors.toList()),
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

    private void readLoadColumnsAndSaveData(Row row, List<SubjectStudyLoadColumn> columns, K3SubjectEntity subject) {
        SubjectStudyLoad results = studyLoadResultsDao.findBySubjectInfoAndSourceOfFinancingAndFormOfAndEducationForm(
                subject.getSubjectInfo(), this.sourceOfFinancing, this.educationForm
        ).orElseGet(() -> this.newSubjectLoad(subject.getSubjectInfo()));

        columns.stream()
                .filter(Objects::nonNull)
                .forEach(col -> col.setFormulaResultValueToStudyLoadResultObj(row, results));

        studyLoadResultsDao.save(results);
    }

    private SubjectStudyLoad newSubjectLoad(SubjectInfo subjectInfo) {
        var studyLoadResults = new SubjectStudyLoad();
        studyLoadResults.setSubjectInfo(subjectInfo);
        studyLoadResults.setEducationForm(this.educationForm);
        studyLoadResults.setSourceOfFinancing(this.sourceOfFinancing);
        return studyLoadResultsDao.save(studyLoadResults);
    }

    private Row getNextRowForGiven(Row firstRowOfSemesterSection) {
        int rowNum = firstRowOfSemesterSection.getRowNum();
        Sheet sheet = firstRowOfSemesterSection.getSheet();
        return sheet.getRow(rowNum + 1);
    }
}
