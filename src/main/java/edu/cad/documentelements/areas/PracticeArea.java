package edu.cad.documentelements.areas;

import edu.cad.documentelements.columns.AbstractColumn;
import edu.cad.documentelements.columns.SimpleColumn;
import edu.cad.entities.Curriculum;
import edu.cad.entities.Practice;
import edu.cad.entities.Workplan;
import edu.cad.utils.documentutils.DateIntervalStringCreator;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class PracticeArea extends AbstractDocumentArea {
    
    private final List<AbstractColumn> columns;

    public PracticeArea(Sheet sheet, int startRow) {
        super(sheet, "practice", startRow);
        this.columns = new ArrayList<>();
        
        addColumns();
    }
    
    private void addColumns() {
        Row row = sheet.getRow(rowNumber);
        columns.add(new SimpleColumn(row, "p_num"));
        columns.add(new SimpleColumn(row, "practice"));
        columns.add(new SimpleColumn(row, "p_dates"));
        columns.add(new SimpleColumn(row, "p_weeks"));
        columns.add(new SimpleColumn(row, "p_semester"));
    }

    @Override
    public void fill(Curriculum curriculum) {
        Practice practice = ((Workplan)curriculum).getPractice();
        fillColumns(practice);        
    }

    private void fillColumns(Practice practice) {
        Row row = sheet.getRow(rowNumber);
        // order the same as in constructor
        columns.get(0).fill(row, 1);
        columns.get(1).fill(row, practice.getDenotation());
        columns.get(2).fill(row, DateIntervalStringCreator.getDatesString(
                                practice.getStart(), practice.getFinish()));
        columns.get(3).fill(row, practice.getWeeks());
        columns.get(4).fill(row, practice.getSemester());
    }    
}
