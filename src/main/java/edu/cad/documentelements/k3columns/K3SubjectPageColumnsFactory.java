package edu.cad.documentelements.k3columns;

import edu.cad.utils.documentutils.CellWithTokenValidator;
import edu.cad.utils.k3.SourceOfFinancing;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;
import java.util.Map;

import static edu.cad.utils.documentutils.ColumnTokenStringSplitter.K3_ST_LOAD_TOKEN_BEGINNING;
import static edu.cad.utils.documentutils.ColumnTokenStringSplitter.K3_WP_TOKEN_BEGINNING;

public class K3SubjectPageColumnsFactory {

    public static void createAndAddColumn(Map<Class, List<AbstractK3Column>> dest, Cell cell, SourceOfFinancing mainSource) {
        if (null != CellWithTokenValidator.getContentIfCellValid(cell, K3_WP_TOKEN_BEGINNING)) {
            dest.get(AbstractK3Column.class)
                    .add(K3WPColumnsFactory.createColumn(cell, mainSource));
        } else if (null != CellWithTokenValidator.getContentIfCellValid(cell, K3_ST_LOAD_TOKEN_BEGINNING)) {
            dest.get(SubjectStudyLoadColumn.class)
                    .add(SubjectStudyLoadColumnsFactory.createColumnAndSetFormula(cell));
        }
                    
    }
}
