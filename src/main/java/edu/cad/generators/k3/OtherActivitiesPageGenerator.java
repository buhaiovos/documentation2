package edu.cad.generators.k3;

import org.apache.poi.ss.usermodel.Sheet;

public class OtherActivitiesPageGenerator extends FormK3Generator {
    @Override
    public boolean canGenerate(Sheet sheet) {
        return false;
    }

    @Override
    public void fillInSheet(Sheet sheet) {
        throw new UnsupportedOperationException("To be implemented...");
    }
}
