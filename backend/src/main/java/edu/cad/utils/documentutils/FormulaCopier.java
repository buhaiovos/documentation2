package edu.cad.utils.documentutils;

import org.apache.poi.hssf.usermodel.HSSFEvaluationWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.FormulaParser;
import org.apache.poi.ss.formula.FormulaRenderer;
import org.apache.poi.ss.formula.FormulaType;
import org.apache.poi.ss.formula.ptg.AreaPtg;
import org.apache.poi.ss.formula.ptg.Ptg;
import org.apache.poi.ss.formula.ptg.RefPtgBase;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;

public class FormulaCopier {

    private FormulaCopier() {
        // helper class shouldn't have constructor
    }

    public static void copyFormula(Sheet sheet, Cell src, Cell dest) {
        if (!isInputValid(sheet, src, dest)) {
            return;
        }
        
        String formula = src.getCellFormula();
        int shiftRows = dest.getRowIndex() - src.getRowIndex();
        int shiftCols = dest.getColumnIndex() - src.getColumnIndex();

        HSSFEvaluationWorkbook workbookWrapper = 
                HSSFEvaluationWorkbook.create((HSSFWorkbook) sheet.getWorkbook());

        Ptg[] ptgs = FormulaParser.parse(formula, workbookWrapper, FormulaType.CELL,
                sheet.getWorkbook().getSheetIndex(sheet));
        
        for (Ptg ptg : ptgs) {
            if (ptg instanceof RefPtgBase) { // base class for cell references
                shiftCellReferences(ptg, shiftCols, shiftRows);
            } 
            else if (ptg instanceof AreaPtg) { // base class for range references
                shiftAreaReferences(ptg, shiftCols, shiftRows);
            }
        }
        formula = FormulaRenderer.toFormulaString(workbookWrapper, ptgs);
        dest.setCellFormula(formula);
    }

    /**
     * Use to correct formula which was shifted by template extension but itself
     * remain to reference previous
     *
     * @param sheet       current sheet of .xls workbook
     * @param formulaCell cell which contains formula which should be corrected
     */
    public static void correctFormula(Sheet sheet, Cell formulaCell) {
        HSSFWorkbook workbook = (HSSFWorkbook) sheet.getWorkbook();

        HSSFEvaluationWorkbook evaluationWorkbook = HSSFEvaluationWorkbook.create(workbook);
        Ptg[] formulaParts = FormulaParser.parse(formulaCell.getCellFormula(), evaluationWorkbook, FormulaType.CELL,
                workbook.getSheetIndex(sheet));

        for (Ptg formulaPart : formulaParts) {
            setCellReferencesForRow(formulaPart, formulaCell.getRowIndex());
        }

        String correctedFormula = FormulaRenderer.toFormulaString(evaluationWorkbook, formulaParts);
        formulaCell.setCellFormula(correctedFormula);
    }

    private static void setCellReferencesForRow(Ptg formulaPart, int targetRowIndex) {

        if ((formulaPart instanceof RefPtgBase) && ((RefPtgBase) formulaPart).isRowRelative()) {
            ((RefPtgBase) formulaPart).setRow(targetRowIndex);
        }
    }

    private static boolean isInputValid(Sheet sheet, Cell src, Cell dest) {
        if (src == null || dest == null || sheet == null
                || src.getCellType() != CellType.FORMULA) {
            return false;
        }
        return !src.isPartOfArrayFormulaGroup();
    }

    private static void shiftCellReferences(Ptg ptg, int shiftCols, int shiftRows) {
        RefPtgBase ref = (RefPtgBase) ptg;
        if (ref.isColRelative()) {
            ref.setColumn(ref.getColumn() + shiftCols);
        }
        if (ref.isRowRelative()) {
            ref.setRow(ref.getRow() + shiftRows);
        }
    }

    private static void shiftAreaReferences(Ptg ptg, int shiftCols, int shiftRows) {
        AreaPtg ref = (AreaPtg) ptg;
        if (ref.isFirstColRelative())
             ref.setFirstColumn(ref.getFirstColumn() + shiftCols);
        if (ref.isLastColRelative())
            ref.setLastColumn(ref.getLastColumn() + shiftCols);
        if (ref.isFirstRowRelative())
            ref.setFirstRow(ref.getFirstRow() + shiftRows);
        if (ref.isLastRowRelative())
            ref.setLastRow(ref.getLastRow() + shiftRows);
    }
}
