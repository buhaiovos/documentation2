package edu.cad.documentelements.k3columns;

import edu.cad.daos.HibernateDAO;
import static edu.cad.documentelements.k3columns.K3WPColumnTokens.*;
import edu.cad.entities.ControlDictionary;
import edu.cad.entities.Subject;
import edu.cad.utils.k3.SourceOfFinancing;
import edu.cad.utils.k3.TypeOfGroupWork;
import edu.cad.utils.documentutils.CellWithTokenValidator;
import edu.cad.utils.documentutils.ColumnTokenStringSplitter;
import org.apache.poi.ss.usermodel.Cell;

public class K3WPColumnsFactory {
    private static final String TOKEN_BEGINNING = 
            ColumnTokenStringSplitter.K3_WP_TOKEN_BEGINNING;
    
    public static AbstractK3Column createColumn(Cell cell, 
            SourceOfFinancing mainSource) {
        
        if (cell != null) {             
            String cellContent = 
                    CellWithTokenValidator.getContentIfCellValid(cell,
                            TOKEN_BEGINNING);
            if (cellContent != null) {
                ColumnTokenStringSplitter ctss = 
                        new ColumnTokenStringSplitter(cellContent);
                               
                if (ctss.getType().equals(FULL_TITLE)) {
                    return createTitleColumn(cell);
                } 
                else {
                    AbstractK3Column column = createColumn(cell.getColumnIndex(), 
                            ctss.getType(), mainSource);
                    
                    if(column == null){
                        column = new FullTitleColumn(cell.getColumnIndex(), "");
                        column.clear(cell.getRow());
                        return null;
                    }
                    
                    return column;
                }
            }      
        }  
        return null;
    }

    private static AbstractK3Column createColumn(int columnIndex, 
            String typeStr, SourceOfFinancing mainSource) {
        
        HibernateDAO<ControlDictionary> controlDAO = 
                new HibernateDAO<>(ControlDictionary.class);
        switch (typeStr) {
            case SEM_HOURS:
                return new HoursK3Column(columnIndex, 
                        Subject::getEctsHoursWithoutExam);
            case LECTIONS:
                return new HoursK3Column(columnIndex,  Subject::getLections);
            case PRACTICE:
                return new HoursK3Column(columnIndex, Subject::getPractices);
            case LABS:
                return new HoursK3Column(columnIndex, Subject::getLabs);
            case INDIVIDUALS: // <<<<<<<=================================== NOT SUPPORTED
                //return new HoursK3Column(columnIndex, null);
                System.out.println("Individuals");
                break;
                
            case EXAMS:
                return new ControlK3Column(columnIndex, controlDAO.get(1));
            case CREDITS:
                return new ControlK3Column(columnIndex, controlDAO.get(2));
            case CONTROL_WORKS:
                return new ControlK3Column(columnIndex, controlDAO.get(3));
            case COURSE_PROJS:
                return new ControlK3Column(columnIndex, controlDAO.get(4));
            case COURSE_WORKS:
                return new ControlK3Column(columnIndex, controlDAO.get(5));
            case RGRS:
                return new ControlK3Column(columnIndex, controlDAO.get(6));
            case DKR:
                return new ControlK3Column(columnIndex, controlDAO.get(7));
            case REFERATS:
                return new ControlK3Column(columnIndex, controlDAO.get(8));
                
            case AC_GROUPS:
                return new GroupsK3Column(columnIndex, mainSource,
                        TypeOfGroupWork.Academic);
            case SUBGR_PRACT:
                return new GroupsK3Column(columnIndex, mainSource, 
                        TypeOfGroupWork.Practice);
            case SUBGR_LABS:
                return new GroupsK3Column(columnIndex, mainSource, 
                        TypeOfGroupWork.Lab);
            case AC_GROUPS_OTHER:
                return new GroupsK3Column(columnIndex, getOtherSource(mainSource), 
                        TypeOfGroupWork.OtherSource);
                
            case BUDG_GR_BUDG_STUD:
                return new StudentsK3Column(columnIndex, 
                        SourceOfFinancing.Budgetary, SourceOfFinancing.Budgetary);
            case BUDG_GR_CONT_STUD:
                return new StudentsK3Column(columnIndex, 
                        SourceOfFinancing.Budgetary, SourceOfFinancing.Contract);
            case CONT_GR_BUDG_STUD:
                return new StudentsK3Column(columnIndex, 
                        SourceOfFinancing.Contract, SourceOfFinancing.Budgetary);
            case CONT_GR_CONT_STUD:
                return new StudentsK3Column(columnIndex, 
                        SourceOfFinancing.Contract, SourceOfFinancing.Contract);
                
            case STREAM:
                return new K3StreamColumn(columnIndex);
        }
        return null;        
    }

    private static AbstractK3Column createTitleColumn(Cell cell) {
        int columnIndex = cell.getColumnIndex();
        String faculty = cell.getStringCellValue().split("_")[1];
        
        return new FullTitleColumn(columnIndex, faculty);
    }

    private static SourceOfFinancing getOtherSource(SourceOfFinancing mainSource) {
        switch (mainSource) {
            case Budgetary:
                return SourceOfFinancing.Contract;
            case Contract:
                return SourceOfFinancing.Budgetary;
            default:
                return null;
        }
    }
    
}
