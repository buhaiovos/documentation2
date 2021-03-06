package edu.cad.documentelements.areas.k3;

import edu.cad.documentelements.AbstractDocumentElement;
import edu.cad.documentelements.k3columns.AbstractOtherLoadColumn;
import edu.cad.domain.ObjectOfWork;
import edu.cad.domain.OtherLoadType;
import edu.cad.entities.AcademicGroup;
import edu.cad.entities.EducationForm;
import edu.cad.entities.OtherLoad;
import edu.cad.entities.OtherLoadInfo;
import edu.cad.study.load.other.OtherLoadInfoRepositoryWrapper;
import edu.cad.study.load.other.OtherLoadRepositoryWrapper;
import edu.cad.utils.k3.SourceOfFinancing;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType.STRING;

public abstract class K3OtherStudyLoadArea extends AbstractDocumentElement {
    private static final int SEARCH_START_ROW = 3;

    Map<String, Row> tokenToRow = new HashMap<>();
    Set<String> acceptableTokens = new HashSet<>();
    Map<Integer, List<AbstractOtherLoadColumn>> semesterNumToColumns;

    SourceOfFinancing sourceOfFinancing;
    EducationForm educationForm;

    OtherLoadRepositoryWrapper otherLoadDao;
    OtherLoadInfoRepositoryWrapper otherLoadInfoDao;

    K3OtherStudyLoadArea(Map<Integer, List<AbstractOtherLoadColumn>> semesterNumToColumns) {
        this.semesterNumToColumns = semesterNumToColumns;
    }

    protected abstract void fill();

    public void fill(Sheet sheet, EducationForm formOfEducation, SourceOfFinancing sourceOfFinancing) {
        this.sourceOfFinancing = sourceOfFinancing;
        this.educationForm = formOfEducation;

        findRowsOnSheet(sheet);
        if (!tokenToRow.isEmpty()) {
            fill();
        }
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

    OtherLoad createAndSaveOtherLoad(OtherLoadType type, ObjectOfWork object) {
        var otherLoad = new OtherLoad();
        otherLoad.setLoadType(type);
        otherLoad.setObjectOfWork(object);
        return otherLoadDao.save(otherLoad);
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

        return otherLoadInfoDao.save(otherLoadInfo);
    }
}
