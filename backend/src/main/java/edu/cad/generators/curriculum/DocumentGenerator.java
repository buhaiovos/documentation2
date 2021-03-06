package edu.cad.generators.curriculum;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;

public abstract class DocumentGenerator {
    public Workbook generate(InputStream template) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(template);
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            fillInSheet(workbook.getSheetAt(i));
        }
        return workbook;
    }

    public boolean canGenerate(Sheet sheet) {
        throw new UnsupportedOperationException("Method should be implemented");
    }

    public abstract void fillInSheet(Sheet sheet);
}
