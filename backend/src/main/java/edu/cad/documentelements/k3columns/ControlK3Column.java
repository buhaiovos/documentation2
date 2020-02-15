package edu.cad.documentelements.k3columns;

import edu.cad.entities.ControlDictionary;
import edu.cad.study.control.dictionary.ControlDictionaryService;
import edu.cad.study.subject.info.SubjectInfoService;
import edu.cad.utils.k3.K3SubjectEntity;

import static edu.cad.entities.ControlDictionary.CREDIT_ID;
import static edu.cad.entities.ControlDictionary.DIFFERENTIATED_CREDIT_ID;

public class ControlK3Column extends AbstractK3Column {
    private final ControlDictionary control;
    private ControlDictionaryService controlDictionaryService;
    private SubjectInfoService subjectInfoService;

    public ControlK3Column(int columnNumber, ControlDictionary control) {
        super(columnNumber);
        this.control = control;
    }

    @Override
    public String getValue(K3SubjectEntity subject) {
        if (control.getId() == 5) {
            if (subjectInfoService.hasCourseWork(subject.getSubjectInfo())) {
                return "3";
            }
        }

        if (subject.getSubjectInfo().hasControlOfType(control)) {
            return "1";
        }

        if (control.getId() == CREDIT_ID) {
            ControlDictionary diff = controlDictionaryService.findById(DIFFERENTIATED_CREDIT_ID).orElseThrow();
            if (subject.getSubjectInfo().hasControlOfType(diff)) {
                return "1";
            }
        }

        return "0";
    }

}
