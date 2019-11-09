package edu.cad.documentelements.k3columns;

import edu.cad.daos.HibernateDao;
import edu.cad.entities.ControlDictionary;
import edu.cad.entities.SubjectInfo;
import edu.cad.utils.documentutils.CellWithTokenValidator;
import edu.cad.utils.documentutils.ColumnTokenStringSplitter;
import edu.cad.utils.k3.SourceOfFinancing;
import edu.cad.utils.k3.TypeOfGroupWork;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;

import static edu.cad.documentelements.k3columns.K3WPColumnTokens.*;
import static edu.cad.entities.ControlDictionary.*;
import static edu.cad.utils.documentutils.ColumnTokenStringSplitter.K3_WP_TOKEN_BEGINNING;
import static java.lang.String.format;

@Slf4j
class K3WPColumnsFactory {
    static AbstractK3Column createColumn(Cell cell, SourceOfFinancing mainSource) {
        if (cell != null) {
            String cellContent = CellWithTokenValidator.getContentIfCellValid(cell, K3_WP_TOKEN_BEGINNING);
            if (cellContent != null) {
                ColumnTokenStringSplitter ctss = new ColumnTokenStringSplitter(cellContent);

                return ctss.getType().equals(FULL_TITLE)
                        ? createTitleColumn(cell)
                        : createColumn(cell.getColumnIndex(), ctss.getType(), mainSource);
            }
        }
        throw new IllegalArgumentException("Null cell");
    }

    @SuppressWarnings("DuplicatedCode")
    private static AbstractK3Column createColumn(int columnIndex, String typeStr, SourceOfFinancing mainSource) {
        HibernateDao<ControlDictionary> controlDAO = new HibernateDao<>(ControlDictionary.class);

        return switch (typeStr) {
            case ORDER_NUM -> new NumberK3Column(columnIndex);
            case SEM_HOURS -> new HoursK3Column(columnIndex, SubjectInfo::getEctsHoursWithoutExam);
            case LECTURES -> new HoursK3Column(columnIndex, SubjectInfo::getLectures);
            case PRACTICE -> new HoursK3Column(columnIndex, SubjectInfo::getPractices);
            case LABS -> new HoursK3Column(columnIndex, SubjectInfo::getLabs);
            case INDIVIDUALS -> new HoursK3Column(columnIndex, SubjectInfo::getIndividualHours);
            case EXAMS -> new ControlK3Column(columnIndex, controlDAO.get(EXAM_ID));
            case CREDITS -> new ControlK3Column(columnIndex, controlDAO.get(CREDIT_ID));
            case CONTROL_WORKS -> new ControlK3Column(columnIndex, controlDAO.get(MODULES_AND_TESTS_ID));
            case COURSE_PROJS -> new ControlK3Column(columnIndex, controlDAO.get(COURSE_PROJECT));
            case COURSE_WORKS -> new ControlK3Column(columnIndex, controlDAO.get(COURSE_WORK));
            case RGRS -> new ControlK3Column(columnIndex, controlDAO.get(CALCULATION_GRAPHIC_ASSIGNMENT));
            case DKR -> new ControlK3Column(columnIndex, controlDAO.get(STATE_TEST));
            case REFERATS -> new ControlK3Column(columnIndex, controlDAO.get(ESSAY));
            case AC_GROUPS -> new GroupsK3Column(columnIndex, mainSource, TypeOfGroupWork.Academic);
            case SUBGR_PRACT -> new GroupsK3Column(columnIndex, mainSource, TypeOfGroupWork.Practice);
            case SUBGR_LABS -> new GroupsK3Column(columnIndex, mainSource, TypeOfGroupWork.Lab);
            case AC_GROUPS_OTHER -> new GroupsK3Column(columnIndex, getOppositeSource(mainSource), TypeOfGroupWork.OtherSource);
            case BUDG_GR_BUDG_STUD -> new StudentsK3Column(columnIndex, SourceOfFinancing.Budgetary, SourceOfFinancing.Budgetary);
            case BUDG_GR_CONT_STUD -> new StudentsK3Column(columnIndex, SourceOfFinancing.Budgetary, SourceOfFinancing.Contract);
            case CONT_GR_BUDG_STUD -> new StudentsK3Column(columnIndex, SourceOfFinancing.Contract, SourceOfFinancing.Budgetary);
            case CONT_GR_CONT_STUD -> new StudentsK3Column(columnIndex, SourceOfFinancing.Contract, SourceOfFinancing.Contract);
            case STREAM -> new K3StreamColumn(columnIndex);
            default -> throw new IllegalArgumentException(format("Unsupported column type: %s", typeStr));
        };
    }

    private static AbstractK3Column createTitleColumn(Cell cell) {
        int columnIndex = cell.getColumnIndex();
        String faculty = cell.getStringCellValue().split("_")[1];

        return new FullTitleColumn(columnIndex, faculty);
    }

    private static SourceOfFinancing getOppositeSource(SourceOfFinancing source) {
        return switch (source) {
            case Budgetary -> SourceOfFinancing.Contract;
            case Contract -> SourceOfFinancing.Budgetary;
        };
    }

}
