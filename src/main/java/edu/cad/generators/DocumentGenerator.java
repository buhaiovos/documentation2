package edu.cad.generators;

import edu.cad.Document;
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

    abstract void fillInSheet(Sheet sheet);

    public static DocumentGenerator forDocumentType(Document document) {
        switch (document) {
            case CURRICULUM:
                return new CurriculumGenerator();
            case WORK_PLAN:
                return new WorkingPlanGenerator();
            case K3:
                return new FormK3Generator();
            default:
                throw new UnsupportedOperationException(document.name());
        }
    }
}
