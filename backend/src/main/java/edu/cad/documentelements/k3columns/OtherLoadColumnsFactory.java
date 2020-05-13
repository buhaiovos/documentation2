package edu.cad.documentelements.k3columns;

import edu.cad.utils.Utils;
import edu.cad.utils.documentutils.ColumnTokenStringSplitter;
import edu.cad.utils.k3.SourceOfFinancing;

import static edu.cad.documentelements.k3columns.K3OtherLoadTokens.*;
import static java.lang.String.format;

public class OtherLoadColumnsFactory {
    public static AbstractOtherLoadColumn getColumn(int columnNumber, String token, SourceOfFinancing source) {
        ColumnTokenStringSplitter tokenSplitter = new ColumnTokenStringSplitter(token);
        String type = tokenSplitter.getType();
        int semester = Utils.parseInteger(tokenSplitter.getFirstNumString()).orElseThrow();

        return switch (type) {
            case FACULTY -> new OtherLoadFacultyColumn(semester, columnNumber);
            case YEAR -> new OtherLoadYearColumn(semester, columnNumber);
            case GROUP -> new OtherLoadGroupCipherColumn(semester, columnNumber);
            case NUMBER_OF_STUDENTS -> new OtherLoadStudentNumberColumn(semester, columnNumber, source);
            case HOURS -> new OtherLoadFormulaColumn(semester, columnNumber);
            default -> throw new IllegalArgumentException(format("Unsupported token: '%s'", token));
        };
    }
}
