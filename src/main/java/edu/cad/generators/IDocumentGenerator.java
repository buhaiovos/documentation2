package edu.cad.generators;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;

public interface IDocumentGenerator {
    
    public void generate() throws IOException;

    public Workbook getTemplate();
    
}
