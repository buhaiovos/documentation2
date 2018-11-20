package edu.cad.documentelements.k3columns;

import edu.cad.documentelements.columns.AbstractColumn;
import edu.cad.entities.CurriculumSubject;
import edu.cad.entities.OtherLoadInfo;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Row;

@Getter
public abstract class AbstractOtherLoadColumn extends AbstractColumn {
    protected int semester;

    public AbstractOtherLoadColumn(int semester, int columnNumber) {
        this.columnNumber = columnNumber;
        this.semester = semester;
    }

    @Override
    public void fill(Row row, CurriculumSubject record) {
        throw new UnsupportedOperationException("Method is not intended for usage in this hierarchy");
    }

    public abstract void fill(Row row, OtherLoadInfo info);

}
