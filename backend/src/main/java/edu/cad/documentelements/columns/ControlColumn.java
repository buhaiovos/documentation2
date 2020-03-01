package edu.cad.documentelements.columns;

import edu.cad.entities.Control;
import edu.cad.entities.ControlDictionary;
import edu.cad.entities.CurriculumSubject;
import edu.cad.entities.SubjectInfo;
import edu.cad.study.control.dictionary.ControlDictionaryService;
import edu.cad.utils.Utils;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static edu.cad.entities.ControlDictionary.CREDIT_ID;
import static edu.cad.entities.ControlDictionary.DIFFERENTIATED_CREDIT_ID;

public class ControlColumn extends AbstractColumn {
    private final ControlDictionary control;
    // I hope this will be removed soon once the Control Dictionary
    // will be replaced as a type with some enumeration
    private final ControlDictionaryService controlDictionaryService;

    public ControlColumn(int columnNumber, ControlDictionary control, ControlDictionaryService cds) {
        super(columnNumber);
        this.control = control;
        this.controlDictionaryService = cds;
    }

    @Override
    public void fill(Row row, CurriculumSubject subject) {
        Set<Control> controls = new TreeSet<>(getSubjectControls(subject, control));

        if (control.getId() == CREDIT_ID) {
            ControlDictionary diff = controlDictionaryService.findById(DIFFERENTIATED_CREDIT_ID).orElseThrow();
            controls.addAll(getSubjectControls(subject, diff));
        }

        writeControls(row, controls);
    }

    public Set<Control> getSubjectControls(CurriculumSubject subject, ControlDictionary type) {
        Set<Control> result = new HashSet<>();

        for (SubjectInfo element : subject.getSubjectInfo().getSubSubjects(subject.getCurriculum())) {
            result.addAll(element.getControlsByType(type));
        }

        return result;
    }

    private void writeControls(Row row, Set<Control> controls) {
        if (controls.isEmpty())
            return;

        Iterator<Control> iterator = controls.iterator();

        StringBuilder value = new StringBuilder();
        value.append(iterator.next().toString());

        if (controls.size() == 1 && Utils.isNumber(value.toString())) {
            fill(row, Integer.parseInt(value.toString()));
            return;
        }

        while (iterator.hasNext()) {
            value.append(",");
            value.append(iterator.next().toString());
        }

        fill(row, value.toString());
    }
}
