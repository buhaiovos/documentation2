package edu.cad.generators;

import java.io.IOException;
import org.apache.poi.ss.usermodel.Workbook;

public interface IDocumentGenerator {
    
    public void generate() throws IOException;    
    public Workbook getWorkbook();
    
}
