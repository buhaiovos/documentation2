package edu.cad.documentelements.areas.k3;

import edu.cad.daos.OtherLoadDao;
import edu.cad.daos.OtherLoadInfoDao;
import edu.cad.documentelements.AbstractDocumentElement;
import edu.cad.documentelements.k3columns.AbstractOtherLoadColumn;
import edu.cad.domain.ObjectOfWork;
import edu.cad.domain.OtherLoadType;
import edu.cad.entities.AcademicGroup;
import edu.cad.entities.EducationForm;
import edu.cad.entities.OtherLoad;
import edu.cad.entities.OtherLoadInfo;
import edu.cad.utils.k3.SourceOfFinancing;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.*;
import java.util.function.ToIntFunction;

import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType.STRING;

public abstract class K3OtherStudyLoadArea extends AbstractDocumentElement {
    private static final int SEARCH_START_ROW = 3;

    Map<String, Row> tokenToRow = new HashMap<>();
    Set<String> acceptableTokens = new HashSet<>();
    Map<Integer, List<AbstractOtherLoadColumn>> semesterNumToColumns;

    SourceOfFinancing sourceOfFinancing;
    EducationForm educationForm;

    OtherLoadDao otherLoadDao = new OtherLoadDao();
    OtherLoadInfoDao otherLoadInfoDao = new OtherLoadInfoDao();

    public K3OtherStudyLoadArea(Map<Integer, List<AbstractOtherLoadColumn>> semesterNumToColumns) {
        this.semesterNumToColumns = semesterNumToColumns;
    }

    protected abstract void fill();

    public void fill(Sheet sheet, EducationForm formOfEducation, SourceOfFinancing sourceOfFinancing) {
        this.sourceOfFinancing = sourceOfFinancing;
        this.educationForm = formOfEducation;

        findRowsOnSheet(sheet);
        fill();
    }

    private void findRowsOnSheet(Sheet sheet) {
        Objects.requireNonNull(acceptableTokens, "Area must have distinctive tokens.");

        for (int rowNum = SEARCH_START_ROW; rowNum < sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                Cell cell = row.getCell(cellNum);
                if (cell != null && cell.getCellTypeEnum().equals(STRING) && cell.getStringCellValue() != null) {
                    String value = cell.getStringCellValue();
                    if (acceptableTokens.contains(value)) {
                        tokenToRow.put(value, row);
                        cell.setCellType(BLANK);
                    }
                }
            }
        }
    }

    ToIntFunction<AcademicGroup> resolveForSourceOfFinancing(SourceOfFinancing sourceOfFinancing) {
        switch (sourceOfFinancing) {
            case Contract:
                return AcademicGroup::getContractStudents;
            case Budgetary:
                return AcademicGroup::getBudgetaryStudents;
            default:
                throw new IllegalArgumentException(sourceOfFinancing.name());
        }
    }

    OtherLoad createAndSaveOtherLoad(OtherLoadType type, ObjectOfWork object) {
        var otherLoad = new OtherLoad();
        otherLoad.setLoadType(type);
        otherLoad.setObjectOfWork(object);

        otherLoadDao.create(otherLoad);

        return otherLoad;
    }

    OtherLoadInfo createAndSaveNewOtherLoadInfo(int semester,
                                                List<AcademicGroup> groupsWhichHaveStudentsOfGivenFinancialSource,
                                                String faculty,
                                                int yearOfEducation,
                                                OtherLoad persistedOtherLoad) {
        var otherLoadInfo = new OtherLoadInfo();
        otherLoadInfo.setYearOfEducation(yearOfEducation);
        otherLoadInfo.setFacultyTitle(faculty);
        otherLoadInfo.setGroups(groupsWhichHaveStudentsOfGivenFinancialSource);
        otherLoadInfo.setSemester(semester);
        otherLoadInfo.setLoadHeader(persistedOtherLoad);
        otherLoadInfo.setEducationForm(educationForm);
        otherLoadInfo.setSourceOfFinancing(sourceOfFinancing);

        otherLoadInfoDao.create(otherLoadInfo);

        return otherLoadInfo;
    }
}
