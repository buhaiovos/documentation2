package edu.cad.services.filenames;

import edu.cad.domain.DocumentType;
import edu.cad.utils.databaseutils.DatabaseSwitcher;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.join;

@Service
@Lazy
@Primary
public class GoogleDriveFileNameResolver implements FileNameResolvingService {
    private static final String DB_YEARS_FILE_NAME = "years.txt";
    private static final String TEMPLATE_FILE_EXTENSION = ".xls";

    private final DatabaseSwitcher databaseSwitcher;

    public GoogleDriveFileNameResolver(@Lazy DatabaseSwitcher databaseSwitcher) {
        this.databaseSwitcher = databaseSwitcher;
    }

    @Override
    public String resolveForDocument(DocumentType documentType) {
        final int databaseYear = databaseSwitcher.getDatabaseYear();
        return join(documentType.name().toLowerCase(), "_", databaseYear, TEMPLATE_FILE_EXTENSION);
    }

    @Override
    public String resolveForDatabaseYearsFile() {
        return DB_YEARS_FILE_NAME;
    }
}
