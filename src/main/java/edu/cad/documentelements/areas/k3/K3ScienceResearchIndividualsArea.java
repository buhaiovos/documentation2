package edu.cad.documentelements.areas.k3;

import edu.cad.documentelements.AbstractDocumentElement;
import edu.cad.domain.FormOfEducation;
import edu.cad.utils.k3.SourceOfFinancing;

import java.util.List;
import java.util.Map;

public class K3ScienceResearchIndividualsArea extends AbstractDocumentElement implements K3OtherStudyLoadAreas {
    private static final String AREA_TOKEN_PREFIX = "k3(O)researchSubject";

    Map<Integer, List<OneSemesterOtherLoadRow>> semesterToLoadRows;

    @Override
    public void fill(FormOfEducation formOfEducation, SourceOfFinancing sourceOfFinancing) {

    }
}