package edu.cad.utils.documentutils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;

public class FormulaExtender {
    
    public static void extendFormula(Sheet sheet, Cell src, Cell dest){
        Cell formulaCell = sheet.getRow(dest.getRowIndex() + 1).getCell(dest.getColumnIndex());
        
        if(!formulaCell.getCellTypeEnum().equals(CellType.FORMULA))
            return;
        
        String formula = formulaCell.getCellFormula();
        formula = formula.replaceAll(":" + src.getAddress().toString(), 
                ":" + dest.getAddress().toString());
        formulaCell.setCellFormula(formula);
    }
}
