package edu.cad.documentelements.areas;

import edu.cad.documentelements.columns.AbstractColumn;
import edu.cad.documentelements.columns.SimpleColumn;
import edu.cad.entities.Curriculum;
import edu.cad.entities.StateCertification;
import edu.cad.entities.WorkingPlan;
import edu.cad.utils.documentutils.DateIntervalStringCreator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

public class StateCertificationArea extends AbstractDocumentArea {
    
    private final List<AbstractColumn> columns;
    
    public StateCertificationArea(Sheet sheet, int startRow) {
        super(sheet, "state_cert", startRow);
        
        this.columns = new ArrayList<>();
        addColumns();        
    }
    
    private void addColumns() {
        Row row = sheet.getRow(rowNumber);
        columns.add(new SimpleColumn(row, "sc_num"));
        columns.add(new SimpleColumn(row, "state_cert"));
        columns.add(new SimpleColumn(row, "sc_dates"));
    }

    @Override
    public void fill(Curriculum curriculum) {
        StateCertification sc = ((WorkingPlan) curriculum).getStateCertification();
        fillColumns(sc);
    }
    
    private void fillColumns(StateCertification sc) {
        Row row = sheet.getRow(rowNumber);
        columns.get(0).fill(row, 1);
        columns.get(1).fill(row, sc.getForm());
        columns.get(2).fill(row, DateIntervalStringCreator.getDatesString(
            sc.getStart(), sc.getFinish()));
    }
}
