package edu.cad.documentelements.columns;

import edu.cad.entities.ControlDictionary;
import edu.cad.entities.SubjectInfo;
import edu.cad.entities.interfaces.SubjectProperty;
import edu.cad.study.control.dictionary.ControlDictionaryService;
import edu.cad.utils.documentutils.CellWithTokenValidator;
import edu.cad.utils.documentutils.ColumnTokenStringSplitter;
import org.apache.poi.ss.usermodel.Cell;

import static java.lang.String.format;

public class ColumnFactory {

    private static final String TOKEN_BEGINNING = ColumnTokenStringSplitter.CURRICULUM_TOKEN_BEGINNING;

    private static ControlDictionaryService controlDictionaryService;

    public static void setControlDictionaryService(ControlDictionaryService cds) {
        controlDictionaryService = cds;
    }

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

        return switch (tokenStringSplitter.getType()) {
            case "cipher" -> new CipherColumn(columnNumber);
            case "control" -> controlColumn(columnNumber, tokenStringSplitter);
            case "department" -> new DepartmentColumn(columnNumber);
            case "ects" -> new HoursColumn(columnNumber, SubjectInfo::getEcts);
            case "labs" -> new HoursColumn(columnNumber, SubjectInfo::getLabs);
            case "lectures" -> new HoursColumn(columnNumber, SubjectInfo::getLectures);
            case "practices" -> new HoursColumn(columnNumber, SubjectInfo::getPractices);
            case "actlabs" -> new HoursColumn(columnNumber, SubjectInfo::getActualLabs);
            case "actlectures" -> new HoursColumn(columnNumber, SubjectInfo::getActualLectures);
            case "actpractices" -> new HoursColumn(columnNumber, SubjectInfo::getActualPractices);
            case "individuals" -> new HoursColumn(columnNumber, SubjectInfo::getIndividualHours);
            case "section" -> new TitleColumn(columnNumber);
            case "semester" -> semesterColumn(columnNumber, tokenStringSplitter, SubjectInfo::getTotalHours);
            case "semlabs" -> semesterColumn(columnNumber, tokenStringSplitter, SubjectInfo::getLabs);
            case "semlectures" -> semesterColumn(columnNumber, tokenStringSplitter, SubjectInfo::getLectures);
            case "sempractices" -> semesterColumn(columnNumber, tokenStringSplitter, SubjectInfo::getPractices);
            default -> throw new IllegalArgumentException(format("Unsupported column type: <%s>",
                    tokenStringSplitter.getType()));
        };
    }

    private static AbstractColumn controlColumn(int columnNumber, ColumnTokenStringSplitter tokenStringSplitter) {
        int id = Integer.parseInt(tokenStringSplitter.getFirstNumString());
        ControlDictionary cd = controlDictionaryService.findById(id).orElseThrow();
        return new ControlColumn(columnNumber, cd, controlDictionaryService);
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
