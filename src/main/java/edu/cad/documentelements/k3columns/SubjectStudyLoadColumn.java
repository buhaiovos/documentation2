package edu.cad.documentelements.k3columns;

import edu.cad.entities.SubjectStudyLoad;
import edu.cad.utils.k3.K3SubjectEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.function.BiConsumer;

public class SubjectStudyLoadColumn extends AbstractK3Column {
    private final StudyLoadType type;
    private final BiConsumer<SubjectStudyLoad, Double> propertySetter;

    public SubjectStudyLoadColumn(int columnNumber, StudyLoadType type, BiConsumer<SubjectStudyLoad, Double> propertySetter) {
        super(columnNumber);
        this.type = type;
        this.propertySetter = propertySetter;
    }

    public StudyLoadType getColumnType() {
        return this.type;
    }

    @Override
    public String getValue(K3SubjectEntity subject) {
        throw new UnsupportedOperationException("Most likely you don't need this value use getFormulaResultValue() " +
                "method instead.");
    }

    @Override
    public void fill(Row row, K3SubjectEntity subject, String value) {
        throw new UnsupportedOperationException("Should not be filled, since contains formula.");

    }

    public void setFormulaResultValueToStudyLoadResultObj(Row row, SubjectStudyLoad results) {
        Cell currentCell = row.getCell(columnNumber);
        double cellValue = getValue(currentCell);
        propertySetter.accept(results, cellValue);
    }

    private double getValue(Cell currentCell) {
        if (currentCell.getCachedFormulaResultTypeEnum().equals(CellType.STRING)) {
            return 0;
        } else {
            return currentCell.getNumericCellValue();
        }
    }

}
