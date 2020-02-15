package edu.cad.documentelements.areas;

import edu.cad.documentelements.columns.AbstractColumn;
import edu.cad.documentelements.columns.SimpleColumn;
import edu.cad.entities.Curriculum;
import edu.cad.entities.DiplomaPreparation;
import edu.cad.entities.WorkType;
import edu.cad.entities.WorkingPlan;
import edu.cad.study.worktype.WorkTypeService;
import edu.cad.utils.Utils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.Map;

public class DiplomaPreparationArea extends AbstractDocumentArea {
    private final Map<String, AbstractColumn> columns;
    private WorkTypeService workTypeService;

    public DiplomaPreparationArea(Sheet sheet, int startRow) {
        super(sheet);
        columns = new HashMap<>();
    }

    @Override
    public void fill(Curriculum curriculum) {
        if (findRowNumber(this.rowNumber, "#work")) {
            initializeColumns();
            fillWorkArea(curriculum);
        }
        while (findRowNumber(this.rowNumber, "#work")) {
            fillWorkArea(curriculum);
        }
    }

    private void fillWorkArea(Curriculum curriculum) {
        Row currentRow = sheet.getRow(rowNumber);
        int workColumnNumber = findInRow(currentRow, "#work");
        boolean filled = false;

        WorkType workType = getWorkType(currentRow, workColumnNumber);
        if (workType == null)
            return;

        for (DiplomaPreparation preparation : ((WorkingPlan) curriculum).getDiplomaPreparations()) {
            if (preparation.getWorkType().equals(workType)) {
                columns.put("norm", new SimpleColumn(currentRow, "#work"));
                fillColumns(preparation);
                filled = true;
                break;
            }
        }

        if (!filled) {
            columns.get("norm").clear(currentRow);
        }
    }

    private void initializeColumns() {
        Row row = sheet.getRow(rowNumber);
        columns.put("department", new SimpleColumn(row, "#work_department"));
        columns.put("budgetary", new SimpleColumn(row, "#work_budgetary"));
        columns.put("contract", new SimpleColumn(row, "#work_contract"));
    }

    private WorkType getWorkType(Row row, int columnNumber) {
        String cellContent = row.getCell(columnNumber).getStringCellValue();
        String workId = cellContent.replaceAll("#work", "");

        if (!Utils.isNumber(workId)) {
            return null;
        }
        return workTypeService.findById(Integer.parseInt(workId)).orElseThrow();
    }

    private void fillColumns(DiplomaPreparation preparation) {
        Row row = sheet.getRow(rowNumber);
        WorkingPlan workplan = preparation.getWorkingPlan();

        columns.get("norm").fill(row, preparation.getNorm());
        columns.get("department").fill(row, preparation.getDepartment().getDenotation());
        columns.get("budgetary").fill(row, workplan.countBudgetaryStudents());
        columns.get("contract").fill(row, workplan.countContractStudents());
        HSSFFormulaEvaluator.evaluateAllFormulaCells(sheet.getWorkbook());
    }
}
