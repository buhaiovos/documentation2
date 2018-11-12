package edu.cad.utils.documentutils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;

public class RowInserter {

    public static void insertRow(Row row) {
        Sheet sheet = row.getSheet();
        int rowNum = row.getRowNum();
        int totalRows = sheet.getLastRowNum();
        Row oldRow = sheet.getRow(rowNum - 1);
        List<CellRangeAddress> addresses = getMergedRegions(sheet, oldRow);

        sheet.shiftRows(rowNum, totalRows, 1);
        shiftMergedRegions(sheet, addresses);

        Row newRow = sheet.getRow(rowNum);
        copyRow(sheet, oldRow, newRow);
    }

    private static void copyRow(Sheet sheet, Row oldRow, Row newRow) {
        newRow.setHeight(oldRow.getHeight());

        for (int i = 0; i < oldRow.getLastCellNum(); i++) {
            Cell oldCell = oldRow.getCell(i);
            Cell newCell = newRow.createCell(i);

            newCell.setCellStyle(oldCell.getCellStyle());
            if (oldCell.getCellTypeEnum().equals(CellType.FORMULA)) {
                FormulaCopier.copyFormula(sheet, oldCell, newCell);
            }

            FormulaExtender.extendFormula(sheet, oldCell, newCell);
        }
    }

    private static void shiftMergedRegions(Sheet sheet, List<CellRangeAddress> addresses) {
        for (CellRangeAddress cellRangeAddress : addresses) {
            int firstColumn = cellRangeAddress.getFirstColumn();
            int lastColumn = cellRangeAddress.getLastColumn();
            int firstRow = cellRangeAddress.getFirstRow() + 1;
            int lastRow = cellRangeAddress.getLastRow() + 1;

            var address = new CellRangeAddress(firstRow, lastRow, firstColumn, lastColumn);
            sheet.addMergedRegion(address);
        }
    }

    private static List<CellRangeAddress> getMergedRegions(Sheet sheet, Row firstRow) {
        List<CellRangeAddress> addresses = new ArrayList<>();

        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() >= firstRow.getRowNum()) {
                addresses.add(cellRangeAddress.copy());
                if (cellRangeAddress.getFirstRow() != firstRow.getRowNum()) {
                    sheet.removeMergedRegion(i--);
                }
            }
        }

        return addresses;
    }
}
