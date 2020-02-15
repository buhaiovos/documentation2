package edu.cad.documentelements.controlcounters;

import edu.cad.entities.ControlDictionary;
import edu.cad.study.control.dictionary.ControlDictionaryService;
import edu.cad.utils.Utils;
import edu.cad.utils.documentutils.ColumnTokenStringSplitter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class ControlCounterFactory {

    private static ControlDictionaryService controlDictionaryService;

    public static ControlCounter getControlCounter(Cell cell) {
        if (!checkCell(cell))
            return null;

        ColumnTokenStringSplitter splitter = new ColumnTokenStringSplitter(cell.getStringCellValue());
        if (!splitter.getType().equals("semcontrol"))
            return null;

        if (splitter.getFirstNumString() == null || !Utils.isNumber(splitter.getFirstNumString()))
            return null;

        int controlType = Integer.parseInt(splitter.getFirstNumString());
        ControlDictionary control = controlDictionaryService.findById(controlType).orElseThrow();
        if (splitter.getSecondNumString() == null || !Utils.isNumber(splitter.getSecondNumString()))
            return new ControlCounter(cell, control);

        int semester = Integer.parseInt(splitter.getSecondNumString());
        return new SemesterControlCounter(cell, control, semester);
    }

    private static boolean checkCell(Cell cell) {
        if (cell == null || !cell.getCellTypeEnum().equals(CellType.STRING))
            return false;

        String value = cell.getStringCellValue();

        return value.startsWith("#") && value.contains("_");
    }
}
