package edu.cad.documentelements.controlcounters;

import edu.cad.entities.ControlDictionary;
import edu.cad.entities.Curriculum;
import org.apache.poi.ss.usermodel.Cell;

import static edu.cad.entities.ControlDictionary.CREDIT_ID;
import static edu.cad.entities.ControlDictionary.DIFFERENTIATED_CREDIT_ID;

public class SemesterControlCounter extends ControlCounter {
    private final int semester;

    public SemesterControlCounter(Cell cell, ControlDictionary control, int semester) {
        super(cell, control);
        this.semester = semester;
    }

    @Override
    public void fill(Curriculum curriculum) {
        StringBuilder value = new StringBuilder();
        int count = curriculum.countControlsByType(semester, control);

        if (control.getId() == CREDIT_ID) {
            ControlDictionary diff = dictionaryService.findById(DIFFERENTIATED_CREDIT_ID).orElseThrow();
            int diffCount = curriculum.countControlsByType(semester, diff);

            buildDifferentiatedCreditValuePart(value, count > 0, diffCount);
        }

        if (count > 0)
            value.append(count);

        if (value.length() > 0) {
            cell.setCellValue(value.toString());
        }
    }


}
