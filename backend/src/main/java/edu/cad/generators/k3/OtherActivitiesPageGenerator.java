package edu.cad.generators.k3;

import edu.cad.documentelements.areas.k3.*;
import edu.cad.documentelements.k3columns.AbstractOtherLoadColumn;
import edu.cad.documentelements.k3columns.OtherLoadColumnsFactory;
import edu.cad.entities.Department;
import edu.cad.entities.EducationForm;
import edu.cad.study.department.DepartmentService;
import edu.cad.study.educationform.EducationFormService;
import edu.cad.utils.k3.SourceOfFinancing;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
class OtherActivitiesPageGenerator extends AbstractK3Generator {
    private static final String K3_OTHER_PAGE_COLUMN_TOKEN_BEGINNING = "#k3(OC)";
    private static final String PAGE_TOKEN = "#other";

    private Department department;
    private EducationForm educationForm;
    private SourceOfFinancing sourceOfFinancing;

    public OtherActivitiesPageGenerator(EducationFormService educationFormService, DepartmentService departmentService) {
        super(educationFormService, departmentService);
    }

    @Override
    public boolean canGenerate(Sheet sheet) {
        boolean canGenerate = isPageSpecificTokenPresent(sheet, PAGE_TOKEN)
                && areEducationFormAndFinancialSourceTokensPresent(sheet);

        if (canGenerate) {
            this.department = getDepartment(sheet);
            this.educationForm = getEducationForm(sheet);
            this.sourceOfFinancing = getSourceOfFinancing(sheet);
            Cell pageSpecificTokenCell = getPageSpecificTokenCell(sheet);
            pageSpecificTokenCell.setCellType(CellType.BLANK);
        }

        return canGenerate;
    }

    @Override
    public void fillInSheet(Sheet sheet) {
        // collect columns
        Map<Integer, List<AbstractOtherLoadColumn>> semesterToColumns = findColumns(sheet);

        // construct all areas
        new K3ScienceResearchIndividualsArea(semesterToColumns).fill(sheet, educationForm, sourceOfFinancing);
        new K3PracticeManagementArea(semesterToColumns).fill(sheet, educationForm, sourceOfFinancing);
        new K3AttestationManagementArea(semesterToColumns).fill(sheet, educationForm, sourceOfFinancing);
        new K3ExamWorkArea(semesterToColumns).fill(sheet, educationForm, sourceOfFinancing);
        new K3PostgraduatesManagement(semesterToColumns).fill(sheet, educationForm, sourceOfFinancing);
        new K3DoctoralConsulting(semesterToColumns).fill(sheet, educationForm, sourceOfFinancing);
    }

    private Map<Integer, List<AbstractOtherLoadColumn>> findColumns(Sheet sheet) {
        Map<Integer, List<AbstractOtherLoadColumn>> semesterToColumns = new HashMap<>();
        semesterToColumns.put(1, new LinkedList<>());
        semesterToColumns.put(2, new LinkedList<>());

        Row row = sheet.getRow(1);
        for (int columnNumber = 0; columnNumber < row.getLastCellNum(); columnNumber++) {
            Cell currentCell = row.getCell(columnNumber);
            if (currentCell != null && currentCell.getStringCellValue() != null) {
                String token = currentCell.getStringCellValue();
                if (token.contains(K3_OTHER_PAGE_COLUMN_TOKEN_BEGINNING)) {
                    AbstractOtherLoadColumn column =
                            OtherLoadColumnsFactory.getColumn(columnNumber, token, sourceOfFinancing);
                    currentCell.setBlank();

                    semesterToColumns.get(column.getSemester()).add(column);
                }
            }
        }
        return semesterToColumns;
    }
}
