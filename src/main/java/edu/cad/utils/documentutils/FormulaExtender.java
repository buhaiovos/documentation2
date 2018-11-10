package edu.cad.utils.documentutils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;

public class FormulaExtender {

    public static void extendFormula(Sheet sheet, Cell src, Cell dst) {
        Cell formulaCell = sheet.getRow(dst.getRowIndex() + 1).getCell(dst.getColumnIndex());
        
        if(!formulaCell.getCellTypeEnum().equals(CellType.FORMULA))
            return;
        
        String formula = formulaCell.getCellFormula();
        formula = formula.replaceAll(":" + src.getAddress().toString(), ":" + dst.getAddress().toString());
        formulaCell.setCellFormula(formula);
    }
}
