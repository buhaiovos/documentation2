package edu.cad.documentelements.k3columns;

import edu.cad.entities.OtherLoadInfo;
import edu.cad.utils.documentutils.CellUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class OtherLoadFormulaColumn extends AbstractOtherLoadColumn {
    public OtherLoadFormulaColumn(int semester, int columnNumber) {
        super(semester, columnNumber);
    }

    @Override
    public void fill(Row row, OtherLoadInfo info) {
        Workbook workbook = row.getSheet().getWorkbook();
        HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

        double calculatedValue = CellUtils.getValueFromFormulaCell(row.getCell(columnNumber));
        info.setCalculatedHours(calculatedValue);
    }
}
