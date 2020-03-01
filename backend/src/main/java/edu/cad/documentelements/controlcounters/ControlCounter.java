package edu.cad.documentelements.controlcounters;

import edu.cad.documentelements.AbstractDocumentElement;
import edu.cad.entities.ControlDictionary;
import edu.cad.entities.Curriculum;
import edu.cad.study.control.dictionary.ControlDictionaryService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import static edu.cad.entities.ControlDictionary.CREDIT_ID;
import static edu.cad.entities.ControlDictionary.DIFFERENTIATED_CREDIT_ID;

public class ControlCounter extends AbstractDocumentElement {
    protected final Cell cell;
    protected final ControlDictionary control;

    protected ControlDictionaryService dictionaryService;

    public ControlCounter(Cell cell, ControlDictionary control) {
        this.cell = cell;
        this.control = control;
    }

    public void fill(Curriculum curriculum) {
        StringBuilder value = new StringBuilder();
        int count = curriculum.countControlsByType(control);

        if (control.getId() == CREDIT_ID) {
            ControlDictionary differentiatedCredit =
                    dictionaryService.findById(DIFFERENTIATED_CREDIT_ID).orElseThrow();
            int numberOfDifferentiatedCredits = curriculum.countControlsByType(differentiatedCredit);

            buildDifferentiatedCreditValuePart(value, count > 0, numberOfDifferentiatedCredits);
        }

        if (count > 0)
            value.append(count);

        if (value.length() > 0) {
            cell.setCellValue(value.toString());
        }
    }

    protected void buildDifferentiatedCreditValuePart(StringBuilder value, boolean regularCreditsArePresent,
                                                      int numberOfDifferentiatedCredits) {
        if (numberOfDifferentiatedCredits > 0) {
            value.append(numberOfDifferentiatedCredits);
            value.append('д');

            if (regularCreditsArePresent) {
                value.append('+');
            }
        }
    }

    public void clear() {
        cell.setCellType(CellType.BLANK);
    }
}
