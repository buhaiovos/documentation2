package edu.cad.documentelements.columns;

import edu.cad.daos.HibernateDao;
import edu.cad.entities.ControlDictionary;
import edu.cad.entities.SubjectInfo;
import edu.cad.entities.interfaces.SubjectProperty;
import edu.cad.utils.documentutils.CellWithTokenValidator;
import edu.cad.utils.documentutils.ColumnTokenStringSplitter;
import org.apache.poi.ss.usermodel.Cell;

import static java.lang.String.format;

public class ColumnFactory {

    private static final String TOKEN_BEGINNING = ColumnTokenStringSplitter.CURRICULUM_TOKEN_BEGINNING;

    public static AbstractColumn createColumn(Cell cell) {
        if (cell != null) {
            String cellContent = CellWithTokenValidator.getContentIfCellValid(cell, TOKEN_BEGINNING);
            if (cellContent != null) {
                return createColumn(cellContent, cell.getColumnIndex());
            }
        }
        return null;
    }

    private static AbstractColumn createColumn(String cellContent, int columnNumber) {
        final ColumnTokenStringSplitter tokenStringSplitter = new ColumnTokenStringSplitter(cellContent);

        switch (tokenStringSplitter.getType()) {
            case "cipher":
                return new CipherColumn(columnNumber);
            case "control":
                return controlColumn(columnNumber, tokenStringSplitter);
            case "department":
                return new DepartmentColumn(columnNumber);
            case "ects":
                return new HoursColumn(columnNumber, SubjectInfo::getEcts);
            case "labs":
                return new HoursColumn(columnNumber, SubjectInfo::getLabs);
            case "lectures":
                return new HoursColumn(columnNumber, SubjectInfo::getLectures);
            case "practices":
                return new HoursColumn(columnNumber, SubjectInfo::getPractices);
            case "actlabs":
                return new HoursColumn(columnNumber, SubjectInfo::getActualLabs);
            case "actlectures":
                return new HoursColumn(columnNumber, SubjectInfo::getActualLectures);
            case "actpractices":
                return new HoursColumn(columnNumber, SubjectInfo::getActualPractices);
            case "individuals":
                return new HoursColumn(columnNumber, SubjectInfo::getIndividualHours);
            case "section":
                return new TitleColumn(columnNumber);
            case "semester":
                return semesterColumn(columnNumber, tokenStringSplitter, SubjectInfo::getTotalHours);
            case "semlabs":
                return semesterColumn(columnNumber, tokenStringSplitter, SubjectInfo::getLabs);
            case "semlectures":
                return semesterColumn(columnNumber, tokenStringSplitter, SubjectInfo::getLectures);
            case "sempractices":
                return semesterColumn(columnNumber, tokenStringSplitter, SubjectInfo::getPractices);
            default:
                throw new IllegalArgumentException(format("Unsupported column type: <%s>",
                        tokenStringSplitter.getType()));
        }
    }

    private static AbstractColumn controlColumn(int columnNumber, ColumnTokenStringSplitter tokenStringSplitter) {
        int id = Integer.parseInt(tokenStringSplitter.getFirstNumString());
        ControlDictionary cd = new HibernateDao<>(ControlDictionary.class).get(id);
        return new ControlColumn(columnNumber, cd);
    }

    private static SemesterColumn semesterColumn(int columnNumber, ColumnTokenStringSplitter tokenStringSplitter,
                                                 SubjectProperty property) {
        return new SemesterColumn(
                columnNumber,
                Integer.parseInt(tokenStringSplitter.getFirstNumString()), // semester
                Integer.parseInt(tokenStringSplitter.getSecondNumString()), // weeks
                property
        );
    }
}
